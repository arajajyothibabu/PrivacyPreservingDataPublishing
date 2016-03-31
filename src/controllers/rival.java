import java.io.*;
import java.lang.*;
import java.util.*;
import java.sql.*;
import javax.swing.*;
class Rival
{
	public static void main (String args[])throws IOException
	{
	int i,n;
	ArrayList<Integer> arl=new ArrayList<Integer>();
	quick ss=new quick();
	Statement stmt1=null;
	Connection con=null;
	ResultSet rs=null;
	      try 
	      {
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		con=DriverManager.getConnection("jdbc:odbc:542db","system","lion");
		stmt1 =con.createStatement();
			int xx=stmt1.executeUpdate("update alpha2 set disease_code='1*,3*' where record_id in (select record_id from alpha2 where instr(disease_code,'15')>0 or instr(disease_code,'16')>0 INTERSECT select record_id from alpha2 where instr(disease_code,'31')>0 or instr(disease_code,'32')>0 )");
			int yy=stmt1.executeUpdate("update alpha2 set disease_code='1*' where record_id in (select record_id from alpha2 where instr(disease_code,'15')>0 or instr(disease_code,'16')>0)");


		String sql1="select * from alpha2";
		 
		rs=stmt1.executeQuery(sql1);	
		i=0;
		while(rs.next())
		{
			arl.add(rs.getInt(3));	
			
		}
		n=arl.size();	
		Integer[] a=arl.toArray(new Integer[n]);
		System.out.println("into array");
		for(i=0;i<n;i++)
		{
			System.out.println(a[i]);
		}

		ss.sort(a,0,n-1);
		System.out.println("sorted order");
		for(i=0;i<n;i++)
		{
			System.out.println(a[i]);
		}
		if ((n/2)  >3)
		{
			ss.taxo(a,n/2 +1 ,0);
			ss.taxo(a,n-(n/2 +1),n/2+1);
		}
		else
			ss.taxo(a,n,0);

		con.close();
		stmt1.close();
		//rs.close();
	      }
	      catch(Exception ee)
	      {
		System.out.println(ee);
	      }
	      
	}
}
class quick
{
	int i,j,piv,lw,hi,t,median,index,x,y,z;
	int a[]=new int[30];
	int X[]=new int[30];
	int Y[]=new int[30];
	public void sort(Integer a[],int lw,int hi)
	{
		piv=lw;
		if(lw<hi)
		{
			i=lw;
			j=hi;
			while(i<j)
			{
				while(a[piv]>a[i] && i<hi)
					i++;
				while(a[piv]<a[j] && j>0)
					j--;
				if(i<j)
				{
					t=a[i];
					a[i]=a[j];
					a[j]=t;
				}
		
			}
			if(i>j)
			{
				t=a[piv];
				a[piv]=a[j];
				a[j]=t;
			}
			sort(a,lw,j-1);
			sort(a,j+1,hi);
		}
	}
	public void display(int a[],int n)
	{
		System.out.println("sorted array \n");
		for(i=0;i<n;i++)
			System.out.println(a[i]);
	}

	public void taxo(Integer a[],int n,int low)
	{	
	     try  {
		x=n;
		if(x>3)
		{
			y=(x/2 +low);
			i=0;
			System.out.println("through taxonomy");
			for(i=low;i<y;i++)
			{	
				System.out.println("\t"+a[i]);
				z=a[i];
				String s= "("+a[low]+"-"+ a[y-1]+")";
				call(z,s);
			}
			System.out.println("as "+a[low]+"-"+a[y-1]);
			for(j=y;j<(x+ low);j++)
			{
				System.out.println("\t"+a[j]);
				z=a[j];
				String s="("+a[y]+"-"+a[(x+low)-1]+")";
				call(z,s);
			}
			System.out.println("as "+a[y]+"-"+a[(x+low)-1]);
		}
		else
		{
			for(i=low;i<(x+low);i++)
			{
				System.out.println("\t"+a[i]);
				 z=a[i];
				String s="("+a[low]+"-"+a[(x+low)-1]+")";
				call(z,s);
			}
			System.out.println("as "+a[low]+"-"+a[(x+low)-1]);	
		}

	          }
	          catch(Exception ee)
	          {
		System.out.println(ee);
	          }		
	}
	public void call(int z,String s)
	{
		Statement stmt6=null;
		Connection con=null;
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			 con=DriverManager.getConnection("jdbc:odbc:542db","system","lion");
			String sql4="update alpha2 set age=" + "'" +s+ "'" +"where age="+"'"+z+"'";
			 stmt6=con.createStatement();
			int rr=stmt6.executeUpdate(sql4); 
			stmt6.close();
			con.close();	
	   	}
		catch(Exception ee)
		{
			System.out.println(ee);
		}
		
	}


}