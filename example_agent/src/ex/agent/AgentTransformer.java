package ex.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Constructor;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

public class AgentTransformer implements ClassFileTransformer,Opcodes{

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
	//	System.out.println("[loading] "+ className);
	//	System.out.println("[loader] "+ loader);
	//	System.out.println("[loader.getClass] "+ loader.getClass());
		
		
		if(className.equals("org/apache/ibatis/mapping/MappedStatement")){
			System.out.println("[loadingaaaaaaaaaaaaaaa] "+ className);

			ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS+ClassWriter.COMPUTE_FRAMES);			
	
			try {
	
				String visitorName = "";
	
				if(className != null && !className.equals("")){
					visitorName = "ex/agent/visitor/MappedStatementVisitor";
				}
				
				Class clazz = Class.forName(visitorName.replace("/", "."));
				
				Class[] constructorParameterTypes  = {ClassVisitor.class,String.class};
				Constructor constructor = clazz.getConstructor(constructorParameterTypes);
				
				Object[] constructorParameterObjects = {writer,className};
				constructor.newInstance(constructorParameterObjects);
				
		    	ClassReader cr = new ClassReader(classfileBuffer);
		    	ClassVisitor visitor = (ClassVisitor) constructor.newInstance(constructorParameterObjects);
		    	cr.accept(visitor, ClassReader.SKIP_FRAMES);
		    	
		    	
			} catch (NoSuchMethodException nsme) {
				nsme.printStackTrace();
			//	throw new HyperSpaceException("해당 파라메터로 받는 생성자가 존재하지 않습니다.");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	    	return writer.toByteArray();	    	
		
		}
		return classfileBuffer;
	}

}
