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
	
	public static Map<Long, Double> checkKho(Map<DishInOrderDish, List<GetQuantifierMaterial>> map) {
		
		Map<Long, Double> sumMaterial = null;
		try {
			if(map != null) {
				sumMaterial = new HashMap<Long, Double>();
				for (DishInOrderDish dish : map.keySet()) {
					List<GetQuantifierMaterial> listQuantifier = map.get(dish);
					for (int i = 0; i < listQuantifier.size(); i++) {
						sumMaterial.put(listQuantifier.get(i).getMaterialId(), listQuantifier.get(i).getQuantifier()*dish.getQuantity());	// put vào index
					}
				}								
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sumMaterial;
	}

}
