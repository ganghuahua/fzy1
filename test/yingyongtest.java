import junit.framework.TestCase;

import com.fzy.cms.backend.mode1.Admin;

public class yingyongtest extends TestCase {
	public void test01(){
		Admin a= new Admin();
		a.setId(2);
		Admin b = a;
		b.setId(1);
		a = new Admin();
		a.setId(3);
		
		System.out.print(a.getId()+","+b.getId());			
	}
	
	public void test02(){
		int a = 3;
		String b = "3";
		int c = 3;
		if(b.equals(a)){
			System.out.print("yes");
		}
	}
}
