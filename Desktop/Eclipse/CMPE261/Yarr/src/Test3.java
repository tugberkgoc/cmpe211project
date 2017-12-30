import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.SynchronousQueue;

public class Test3 {
	
	public static HashT<Integer, HashT> calculateSimilarItems(HashT<Integer, HashT> data,int n){
		HashT<Integer, HashT> result = new HashT<>();
		HashT<Integer, HashT> transdata = transformPrefs(data);
		int counter=0;
		for(int movie:transdata.keys()) {
			counter++;
			if(counter%100==0) {
				System.out.println(counter+":"+transdata.size());
				
			}
			HashT<Double, Integer> a=topMatches(transdata, n,movie);
			result.put(movie, a);
			
		}
		return result;
	}
	
	public static HashT<Integer, HashT> transformPrefs(HashT<Integer, HashT> data) {
		HashT<Integer, HashT> result = new HashT<>();

		for (int user : data.keys()) {
			HashT<Integer, Integer> temp = data.get(user);
			for (int movie : temp.keys()) {
				if (result.contains(movie)) {
					result.get(movie).put(user, temp.get(movie));
				} else {
					HashT<Integer, Integer> nestedTree = new HashT<>();
					nestedTree.put(user, temp.get(movie));
					result.put(movie, nestedTree);
				}
			}
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
	
	public static HashT topMatches(HashT<Integer, HashT> data, int n, int person1) {
		HashT<Double, Integer> scores = new HashT<>();
		BST<Double, Integer> temp = new BST<>();
		for (int other : data.keys()) {
			if (other != person1) {
				temp.put(sim_distance(data, person1, other), other);
			}
		}
//		Heap sort=new Heap();
//		Iterable<Double> keys=temp.keys();
//		sort.sort(keys);
		while(n>temp.size()) {
			n--;
		}
		for (int i = 0; i < n; i++) {
			scores.put(temp.max(), temp.get(temp.max()));
			temp.deleteMax();
		}
		return scores;

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
		//ex of trans
//		HashT<Integer, HashT> transdata=transformPrefs(data);
//		for(int key:data.keys()) {
//			HashT<Integer, Integer> nestedTree=data.get(key);
//			for(int movie:nestedTree.keys()) {
//				System.out.println(key+":"+item.get(movie)+":"+nestedTree.get(movie));
//			}
//		}
//		for(int movie:transdata.keys()) {
//			HashT<Integer, Integer> nestedTree=transdata.get(movie);
//			for(int user:nestedTree.keys()) {
//				System.out.println(item.get(movie)+":"+user+":"+nestedTree.get(user));
//			}
//		}
		//ex of intersection
//		HashT<Integer, Integer> deneme=intersection(transdata, 1, 2);
//		for(int user:deneme.keys()) {
//			System.out.println(user);
//		}
		//ex of calcsimitem
//		HashT<Integer, HashT> deneme=calculateSimilarItems(data, 10);
//		for(int movie:deneme.keys()) {
//			HashT nested=deneme.get(movie);
//			for(Object sim:nested.keys()) {
//				double simd=(double) sim;
//				System.out.println(item.get(movie)+":"+simd+","+item.get((int)nested.get(simd)));
//				
//			}
//		}

		
		
	}
}
