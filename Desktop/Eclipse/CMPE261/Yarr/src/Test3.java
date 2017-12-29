import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.SynchronousQueue;

public class Test3 {
	
	public static HashT<HashT,Integer> transformPrefs(HashT<Integer, HashT> data) {
		HashT<Integer, HashT> result = new HashT<>();
		
		for (int i:data.keys()) {
			HashT<Integer, Integer> temp = data.get(i);
			for(int j:data.keys()) {
				result.put(key, val);
			} // it is not finished...! Look at it!!
		}
		return result;
		
	}
	
	public static HashT<Integer, Integer> intersection(HashT<Integer, HashT> data, int person1, int person2) {
		HashT<Integer, Integer> temp = new HashT<>();
		HashT<Integer, Integer> tempPerson1 = data.get(person1);
		HashT<Integer, Integer> tempPerson2 = data.get(person2);

		for (int i : tempPerson1.keys()) {
			if (tempPerson2.contains(i)) {
				temp.put(i, 1);
			}
		}
		return temp;
	}
	
	public static double dist(HashT<Integer, HashT> data,int person1,int person2,int item) {
		int diff=(int) data.get(person1).get(item)-(int) data.get(person2).get(item);
		return Math.pow(diff, 2);
	}
	
	public static double sim_distance(HashT<Integer, HashT> data,int person1,int person2){
		HashT<Integer, Integer> common= new HashT<>();
		HashT<Integer, Integer> tempPerson1=data.get(person1);
		HashT<Integer, Integer> tempPerson2=data.get(person2);
		
		common = intersection(data, person1, person2);
		double counter = 0;
		
		if (common.size()==0) {
			return 0;
		}
		else {
			for(int i: common.keys()) {
				counter+=dist(data, person1, person2, i);
			}
		}
		return 1 / (1 + Math.sqrt(counter));
	}
	
	public static void getRecommendations(HashT<Integer, HashT> data, int person1) {
		for (int other:data.keys()) {
			if(other==person1)continue;
			double sim=sim_distance(data, person1, other);
			if(sim<=0)continue;
			HashT<Integer, Integer> tablePerson= data.get(person1);
			HashT<Integer, Integer> tableOther= data.get(other);
			for(int movie:tableOther.keys()) {
				
			}
		}
	}
	

	
	public static void main(String[] args) {
		HashT<Integer, String> item = new HashT<>();
		
		HashT<Integer, HashT> data = new HashT<Integer, HashT>();
		// Adding Key and Value pairs to data
		// System.out.println(data.containsKey("Key1"));
		// System.out.println(data.get("Key1"));

		// TODO Auto-generated method stub
		// The name of the file to open.
		String fileName[] = new String[3];
		fileName[0] = "u.data";
		fileName[1] = "u.item";

		// This will reference one line at a time
		String line = null;
		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName[1]);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				// System.out.println(line);
				String[] tempData = line.split("\\|");
				item.put(Integer.parseInt(tempData[0]), tempData[1]);

			}

			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName[0]);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				// System.out.println(line);
				String[] tempData = line.split("\t");
				int[] intData = new int[3];
				for (int i = 0; i < intData.length; i++) {
					intData[i] = Integer.parseInt(tempData[i]);
				}
				if (data.contains(intData[0])) {
					data.get(intData[0]).put(intData[1], intData[2]);
				} else {
					HashT<Integer, Integer> nestedTree = new HashT<>();
					nestedTree.put(intData[1], intData[2]);
					data.put(intData[0], nestedTree);
				}
			}

			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}

		HashT<HashT, Integer> a = transformPrefs(data);
		
		for(int k:a.keys()) {
			System.out.println(k);
		}
		System.out.println();
		
		

//		 for(int i:data.keys()){
//		 HashT<Integer, Integer> movie= data.get(i);
//		
//		 for(int c:movie.keys()){
//		 if(c==1){
//		 System.out.println(i+":"+item.get(c)+":"+movie.get(c));
//		 }
//		
//		 }
//		 }
		// for (int s : data.keys()) {
		// LinearProbingHashST<Integer, Integer> yarr= data.get(s);
		// //System.out.println(yarr.keys());
		// for(int i:yarr.keys()){
		// System.out.println(s+":"+ i +":"+yarr.get(i));
		// }
		// }

	}
}
