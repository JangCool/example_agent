package ex.agent.intercept;

import org.objectweb.asm.MethodVisitor;

public interface Interceptor {
	
	/**메소드 로직 실행 전 소스코드 삽입*/
	void insertBefore(MethodVisitor mv);

	/**메소드 로직 실행 후 소스코드 삽입
	 * @return */
	void insertAfter(MethodVisitor mv);
}
