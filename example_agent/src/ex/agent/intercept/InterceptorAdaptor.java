package ex.agent.intercept;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class InterceptorAdaptor extends AdviceAdapter implements Opcodes{
	
	Interceptor mi;
	String name;
	int max;	
	
	
	public InterceptorAdaptor(Interceptor mi, MethodVisitor mv,int access,String name,String desc) {		
		this(mi, mv, access, name, desc, 0);		
	}
	
	public InterceptorAdaptor(Interceptor mi, MethodVisitor mv,int access,String name,String desc,int max) {
			
		super(ASM5, mv, access, name, desc);
		
		this.mi = mi;
		this.name = name;
		this.max = max;
		
	}
	

	//메소드 시작 지점에 소스 입력.
	@Override
	protected void onMethodEnter() {
		mi.insertBefore(mv);
	}

	//메소드 리턴 직전 시점에 소스 등록.
	@Override
	protected void onMethodExit(int opcode) {
//		RETURN
//		메소드 종료와 결과의 반환에 사용
//		RETURN = 종료 하고 void 리턴
//		ARETURN = 종료 하고 Object타입 리턴
		
		if(opcode == ARETURN || opcode == RETURN) {
			mi.insertAfter(mv);
		}
	}
	   
    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        super.visitMaxs(maxStack+max, maxLocals);
    }
 
}
