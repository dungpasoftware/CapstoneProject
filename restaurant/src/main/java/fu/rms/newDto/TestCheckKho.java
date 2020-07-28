package fu.rms.newDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestCheckKho {
	
	
	public static Map<Long, Double> testKho(Map<DishInOrderDish, List<GetQuantifierMaterial>> map) {
		
		Map<Long, Double> sumMaterial = null;
		try {
			if(map != null) {

				sumMaterial = new HashMap<Long, Double>();
				int count = 0;
				for (DishInOrderDish dish : map.keySet()) {
					List<GetQuantifierMaterial> listQuantifier = map.get(dish);
					for (int i = 0; i < listQuantifier.size(); i++) {
						Double sumQuantifierByMaterial = (double) 0;
						if(count==0) {																		// chạy dish lần đầu thì add hết vào
							sumMaterial.put(listQuantifier.get(i).getMaterialId(), listQuantifier.get(i).getQuantifier()*dish.getQuantity());
						}else {
							if(sumMaterial.get(listQuantifier.get(i).getMaterialId()) != null) {		// neu co key roi thi add them vao value cu
								sumQuantifierByMaterial = sumMaterial.get(listQuantifier.get(i).getMaterialId()); // lấy giá trị hiện tại
								sumQuantifierByMaterial += listQuantifier.get(i).getQuantifier()*dish.getQuantity();		// cộng thêm thằng mới
								sumMaterial.replace(listQuantifier.get(i).getMaterialId(), sumQuantifierByMaterial);	// put lại vào
								
							}else {															// nếu chưa có thì put mới
								sumMaterial.put(listQuantifier.get(i).getMaterialId(), listQuantifier.get(i).getQuantifier()*dish.getQuantity());
							}
						}
					}
					count++;															// phân biệt thằng vòng for i = 0 và 1										
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sumMaterial;
	}
	
	
//	public static Long[] checkDuplicate(Long[] list) {
//		Long[] newArray = null;
//		if (list.length==0 || list.length==1){  
//            return list;  
//        }  
//        int j = 0;  
//        for (int i=0; i < list.length; i++){  
//            if (arr[i] != arr[i+1]){  
//                temp[j++] = arr[i];  
//            }  
//         }  
//        temp[j++] = arr[n-1];     
//        // Changing original array  
//        for (int i=0; i<j; i++){  
//            arr[i] = temp[i];  
//        }  
//        return j;  
//    }  
//		
//		
//		return newArray;
//	}

}
