package ex.agent.visitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import ex.agent.intercept.Interceptor;
import ex.agent.intercept.InterceptorAdaptor;

public class MappedStatementVisitor extends ClassVisitor implements Opcodes {


	
	public String className;
	public boolean isStaticClinit;

	public MappedStatementVisitor(ClassVisitor cv,String className) {
		super(ASM5,cv);
		this.className = className;
		
		System.out.println("<clinit>!!!!!!!!!!!!"+getClass().getName());

	}
	
	@Override
	public void visitOuterClass(String arg0, String arg1, String arg2) {
		System.out.println("visitOuterClass arg0 ==> "+arg0);
		System.out.println("visitOuterClass arg1 ==> "+arg1);
		System.out.println("visitOuterClass arg2 ==> "+arg2);
		super.visitOuterClass(arg0, arg1, arg2);
	}
	
	@Override
	public void visitInnerClass(String arg0, String arg1, String arg2, int arg3) {
		System.out.println("visitInnerClass arg0 ==> "+arg0);
		System.out.println("visitInnerClass arg1 ==> "+arg1);
		System.out.println("visitInnerClass arg2 ==> "+arg2);
		System.out.println("visitInnerClass arg3 ==> "+arg3);
		super.visitInnerClass(arg0, arg1, arg2, arg3);
	}
	

	@Override
	public void visit(int arg0, int arg1, String arg2, String arg3, String arg4, String[] arg5) {
		System.out.println("visit arg0 ==> "+arg0);
		System.out.println("visit arg1 ==> "+arg1);
		System.out.println("visit arg2 ==> "+arg2);
		System.out.println("visit arg3 ==> "+arg3);
		System.out.println("visit arg4 ==> "+arg4);
		System.out.println("visit arg5 ==> "+arg5);
		// TODO Auto-generated method stub
		super.visit(arg0, arg1, arg2, arg3, arg4, arg5);
		
	}
	
	@Override
	public FieldVisitor visitField(int arg0, String arg1, String arg2, String arg3, Object arg4) {
		System.out.println("visitField arg0 ==> "+arg0);
		System.out.println("visitField arg1 ==> "+arg1);
		System.out.println("visitField arg2 ==> "+arg2);
		System.out.println("visitField arg3 ==> "+arg3);
		System.out.println("visitField arg4 ==> "+arg4);
		// TODO Auto-generated method stub
		return super.visitField(arg0, arg1, arg2, arg3, arg4);
	}
	//preparedStatement 메소드일 경우 편집.
	public MethodVisitor visitMethod(int access, String name, String desc, String sig, String[] exes) {
		

		MethodVisitor mv = super.visitMethod(access, name, desc, sig, exes);
		
		System.out.println("visitMethod name ==> "+name);

		//static 초기 구문 있는지 체크.
		if("<clinit>".equals(name) && !isStaticClinit){
			isStaticClinit = true;
		}
		
	
		if(name.equals("<init>")){
			mv = new InterceptorAdaptor(new Interceptor() {
				
				@Override
				public void insertBefore(MethodVisitor mv) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void insertAfter(MethodVisitor mv) {				
					mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
					mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
					mv.visitInsn(DUP);
					mv.visitLdcInsn("<clinit>!!!!!!!!!!!!");
					mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V", false);
					mv.visitVarInsn(ALOAD, 0);
					mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false);
					mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getName", "()Ljava/lang/String;", false);
					mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
					mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
					mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
					
				}
			}, mv,access,name,desc);		
		}
		
		if(name.equals("getBoundSql")){
			mv = new InterceptorAdaptor(new Interceptor() {
			
				@Override
				public void insertBefore(MethodVisitor mv) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void insertAfter(MethodVisitor mv) {				
					mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
					mv.visitVarInsn(ALOAD, 2);
					mv.visitMethodInsn(INVOKEVIRTUAL, "org/apache/ibatis/mapping/BoundSql", "getSql", "()Ljava/lang/String;", false);
					mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);		
				}
			}, mv,access,name,desc);		
		
		}		

		return mv;
	}
	
	@Override
	public void visitEnd() {
		System.out.println("visitEnd");

//		
//		if(!isStaticClinit){
//			
//			MethodVisitor mv = super.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
//			mv.visitCode();
//
//			mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//			mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
//			mv.visitInsn(DUP);
//			mv.visitLdcInsn("<clinit>!!!!!! ");
//			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V", false);
//			mv.visitLdcInsn(Type.getType("Lorg/apache/ibatis/mapping/MappedStatement;"));
//			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getName", "()Ljava/lang/String;", false);
//			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
//			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
//			mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
//			
//	        mv.visitInsn(RETURN);
//	        mv.visitMaxs(0, 0);
//			mv.visitEnd();
//		}
//		
		super.visitEnd();
	}
	
	static{
		UNINITIALIZED_THIS.getClass();
	}
}
