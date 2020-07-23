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
						if(count==0) {																							// chạy dish lần đầu thì add hết vào
							sumMaterial.put(listQuantifier.get(i).getMaterialId(), listQuantifier.get(i).getQuantifier()*dish.getQuantity());
						}else {
							if(sumMaterial.get(listQuantifier.get(i).getMaterialId()) != null) {
								sumQuantifierByMaterial = sumMaterial.get(listQuantifier.get(i).getMaterialId());
								sumQuantifierByMaterial += listQuantifier.get(i).getQuantifier()*dish.getQuantity();			// cộng thêm thằng mới
								sumMaterial.replace(listQuantifier.get(i).getMaterialId(), sumQuantifierByMaterial);	
								
							}else {
								sumMaterial.put(listQuantifier.get(i).getMaterialId(), listQuantifier.get(i).getQuantifier()*dish.getQuantity());
							}
						}
					}
					count++;
				}
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return sumMaterial;
	}

}
