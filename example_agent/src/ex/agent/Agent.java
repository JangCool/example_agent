package ex.agent;

import java.lang.instrument.Instrumentation;

public class Agent {


	public static Instrumentation instrumentation;

	public static void premain(String args,Instrumentation inst){

		if (Agent.instrumentation != null) { 
			return; 
		}
		
		intro();
		Agent.instrumentation = inst; 
		Agent.instrumentation.addTransformer(new AgentTransformer()); 

		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {

				while (true) {
					
					
					try {
						Thread.sleep(2000);
						
//						System.out.println("SysJMX.getProcessCPU() => "+SysJMX.getProcessCPU());
//						System.out.println("SysJMX.getProcessPID() => "+SysJMX.getProcessPID());
//						System.out.println("SysJMX.getThreadCPU(id) => "+SysJMX.getThreadCPU(SysJMX.getProcessPID()));
//						System.out.println("SysJMX.isProcessCPU() => "+SysJMX.isProcessCPU());
//						System.out.println("SysJMX.getCurrentThreadAllocBytes() => "+SysJMX.getCurrentThreadAllocBytes());
//						System.out.println("SysJMX.getCurrentThreadCPU() => "+SysJMX.getCurrentThreadCPU());
//						System.out.println("SysJMX.getCurrentThreadCPUnano() => "+SysJMX.getCurrentThreadCPUnano());
//						System.out.println("SysJMX.getCurrentProcGcTime() => "+SysJMX.getCurrentProcGcTime());
//						System.out.println("SysJMX.getCurrentProcGcInfo() => "+SysJMX.getCurrentProcGcInfo());
//						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					
				}

				
			}
		});
		thread.setDaemon(true);
		thread.start();
		

	}
	
	private static void intro() { 
 		try { 
 			System.setProperty("scouter.enabled", "true"); 
 			String nativeName = Agent.class.getName().replace('.', '/') + ".class"; 
 			ClassLoader cl = Agent.class.getClassLoader(); 
 			if (cl == null) { 
 				System.out.println("loaded by system classloader "); 
 				System.out.println(cut("" + ClassLoader.getSystemClassLoader().getResource(nativeName))); 
 			} else { 
 				System.out.println("loaded by app classloader "); 
 				System.out.println(cut("" + cl.getResource(nativeName))); 
 			} 
 		} catch (Throwable t) { 
 		} 
 	} 
  
	private static String cut(String s) { 
		int x = s.indexOf('!'); 
		return x > 0 ? s.substring(0, x) : s; 
	} 

	
	public static Instrumentation getInstrumentation() { 
 		return instrumentation; 
 	} 

}
