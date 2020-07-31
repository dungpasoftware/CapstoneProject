package fu.rms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fu.rms.constant.Constant;
import fu.rms.constant.StatusConstant;
import fu.rms.dto.OrderDishCancelDto;
import fu.rms.dto.OrderDishDto;
import fu.rms.entity.Export;
import fu.rms.entity.ExportMaterial;
import fu.rms.entity.Material;
import fu.rms.entity.Option;
import fu.rms.entity.OrderDish;
import fu.rms.entity.OrderDishOption;
import fu.rms.entity.Status;
import fu.rms.exception.NotFoundException;
import fu.rms.mapper.OrderDishMapper;
import fu.rms.newDto.mapper.OrderDishChef;
import fu.rms.newDto.mapper.OrderDishOptionMapper;
import fu.rms.newDto.DishInOrderDish;
import fu.rms.newDto.GetQuantifierMaterial;
import fu.rms.newDto.OrderDishOptionDtoNew;
import fu.rms.newDto.Remain;
import fu.rms.newDto.SumQuantityAndPrice;
import fu.rms.newDto.TestCheckKho;
import fu.rms.repository.ExportRepository;
import fu.rms.repository.MaterialRepository;
import fu.rms.repository.OptionRepository;
import fu.rms.repository.OrderDishOptionRepository;
import fu.rms.repository.OrderDishRepository;
import fu.rms.repository.OrderRepository;
import fu.rms.repository.StatusRepository;
import fu.rms.request.OrderDishChefRequest;
import fu.rms.request.OrderDishRequest;
import fu.rms.service.IOrderDishService;
import fu.rms.utils.Utils;

@Service
public class OrderDishService implements IOrderDishService {
	
	@Autowired
	OrderDishMapper orderDishMapper;
	
	@Autowired
	OrderDishRepository orderDishRepo;
	
	@Autowired
	OrderDishOptionMapper orderDishOptionMapper;
	
	@Autowired
	OrderDishOptionService orderDishOptionService;
	
	@Autowired
	OrderDishOptionRepository orderDishOptionRepo;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderDishCancelService orderDishCancelService;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	private StatusRepository statusRepo;

	@Autowired
	OptionRepository optionRepo;
	
	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	MaterialRepository materialRepo;
	
	@Autowired
	ExportRepository exportRepo;


	/**
	 * danh sách món ăn trong order
	 */
	@Override
	public List<OrderDishDto> getListOrderDishByOrder(Long orderId) {

		List<OrderDish> listOrderDish = orderDishRepo.findOrderDishByOrder(orderId);
		List<OrderDishDto> listDto = listOrderDish.stream().map(orderDishMapper::entityToDto)
				.collect(Collectors.toList());	
		
		for (int i = 0; i < listOrderDish.size(); i++) {
			List<OrderDishOptionDtoNew> listOrderDishOption = new ArrayList<OrderDishOptionDtoNew>();
			if(listDto.get(i).getOrderDishOptions() != null && listDto.get(i).getOrderDishOptions().size() != 0) {
				
				listOrderDishOption = listOrderDish.get(i).getOrderDishOptions()
				.stream().map(orderDishOptionMapper::entityToDto).collect(Collectors.toList());	;
			}
			listDto.get(i).setOrderDishOptions(listOrderDishOption);
		}
		
		return listDto;
	}

	/**
	 * thêm món khi order
	 */
	@Override
	public Long insertOrderDish(OrderDishDto dto, Long orderId) {

		int result =  0;
		Long orderDishId = (long) 0;
		if(dto != null) {
			result = orderDishRepo.insertOrderDish(orderId, dto.getDish().getDishId(), dto.getComment(),
					dto.getQuantity(), 0, dto.getQuantity(), dto.getSellPrice(), dto.getSumPrice(), dto.getCreateBy(), Utils.getCurrentTime(),
					StatusConstant.STATUS_ORDER_DISH_ORDERED);
		}
		if(result == 1) {
			orderDishId = orderDishRepo.getLastestOrderDishId(orderId);
		}
		return orderDishId;
	}

//	/**
//	 * bếp ấn nấu xong trả lần lượt, nếu trả hết rồi thì trạng thái order cũng thay đổi
//	 */
//	@Override
//	@Transactional
//	public int updateStatusOrderDish(OrderDishChefRequest request, Long statusId) {		// chefStaffId
//		int result = 0;
//		try {
//			result = orderDishRepo.updateStatusOrderDish(statusId, request.getOrderDishId());
//			Long orderId = orderDishRepo.getOrderByOrderDishId(request.getOrderDishId());
//			int count = 0;
//			if(result == 1 && statusId == StatusConstant.STATUS_ORDER_DISH_PREPARATION) {				// tất cả cùng prepare
//				count = getCountStatusOrderDish(orderId, StatusConstant.STATUS_ORDER_DISH_PREPARATION);
//				if(count == 0) {
//					result = orderRepo.updateStatusOrder(StatusConstant.STATUS_ORDER_PREPARATION, orderId);
//				}
//			}
//			if(result == 1 && statusId == StatusConstant.STATUS_ORDER_DISH_COMPLETED) {					// tất cả cùng completed
//				count = getCountStatusOrderDish(orderId, StatusConstant.STATUS_ORDER_DISH_COMPLETED);
//				if(count == 0) {
//					result = orderRepo.updateStatusOrder(StatusConstant.STATUS_ORDER_COMPLETED, orderId);
//				}
//			}
//			simpMessagingTemplate.convertAndSend("/topic/chef", orderService.getListDisplayChefScreen());
//		} catch (NullPointerException e) {
//			return Constant.RETURN_ERROR_NULL;
//		}
//		
//		return result;
//	}

	/**
	 * cập nhật order: order dish: giá, số lượng
	 */
	@Override
	@Transactional
	public String updateQuantityOrderDish(OrderDishDto dto) {
		if(dto!= null) {
			try {
				int addQuantity = 0;
				OrderDish orderDish = orderDishRepo.findById(dto.getOrderDishId())
						.orElseThrow(()-> new NotFoundException("Not found Dish: "+dto.getDish().getDishName()));
																							// tang so luong thi moi xet nvl
				boolean check = true;
				boolean checkIncrease = false;
				boolean checkDecrease = false;
				Map<Long, Double> map = null;
				// tính nguyên vật liệu
				DishInOrderDish dish = new DishInOrderDish();
				dish.setDishId(orderDish.getDish().getDishId());
				dish.setOrderDishId(orderDish.getOrderDishId());
				if(orderDish.getQuantityOk() < dto.getQuantityOk()) {											// tang so luong
					dish.setQuantity(dto.getQuantityOk() - orderDish.getQuantityOk());
					checkIncrease = true;
				}else if(orderDish.getQuantityOk() > dto.getQuantityOk()){
					dish.setQuantity(orderDish.getQuantityOk() - dto.getQuantityOk());							// giam so luong
					checkDecrease = true;
				}else {
					dish.setQuantity(0);
					if(dto.getSellPrice().equals(orderDish.getSellPrice())) {										// chỉ thay đổi giá tiền
						return Constant.NO_CHANGE_DATA;
					}else {
						orderDish.setSellPrice(dto.getSellPrice());														
						orderDish.setSumPrice(dto.getSumPrice());
						orderDish.setModifiedBy(dto.getModifiedBy());
						orderDish.setModifiedDate(Utils.getCurrentTime());
						orderDishRepo.save(orderDish);
						SumQuantityAndPrice sum = getSumQtyAndPriceByOrder(dto.getOrderOrderId());						// cập nhật lại order
						orderService.updateOrderQuantity(sum.getSumQuantity(), sum.getSumPrice(), dto.getOrderOrderId());
						return "";
					}
				}
				if(checkDecrease || checkIncrease) {															// hoặc tăng hoặc giảm
					Map<DishInOrderDish, List<GetQuantifierMaterial>> mapDish = new HashMap<DishInOrderDish, List<GetQuantifierMaterial>>();
					List<GetQuantifierMaterial> listQuantifier = new ArrayList<GetQuantifierMaterial>();
					listQuantifier = orderRepo.getListQuantifierMaterialByDish(dish.getDishId());
					if(listQuantifier.size() != 0) {
						mapDish.put(dish, listQuantifier);
						map = TestCheckKho.checkKho(mapDish);													// tìm ra lượng nvl khi tăng giảm số lượng món ăn
						check=false;
					}
					// tính ra được số lượng nvl
					
					if(checkIncrease&&!check) {																	// nếu có nvl và tăng thì mới check xem trong kho còn ko
						int min = 0;
						for (Long materialId : map.keySet()) {
							Remain remain = materialRepo.getRemainById(materialId);
							Double remainMaterial = remain.getRemain();
							if(map.get(materialId) > remainMaterial) {												// neu nvl can > nvl con lai
								check = true;
								for (GetQuantifierMaterial getQuantifierMaterial : listQuantifier) {				// tìm ra số lượng có thể đủ
									if(getQuantifierMaterial.getMaterialId() == materialId) {
										if(min == 0) {																// lần đầu tìm đc nvl
											double quantity = remainMaterial/getQuantifierMaterial.getQuantifier();
											min = (int) quantity;
										}else {
											double quantity = remainMaterial/getQuantifierMaterial.getQuantifier();
											if((int) quantity < min) {													// tìm dc thằng khác nhỏ hơn
												min = (int) quantity;
											}
										}
										break;
									}
								}																					
							}
						}
						if(check) {																					//có nvl trong món đó ko đủ để thực hiện																					
							String message="";																		// món k đủ nvl
							min += orderDish.getQuantityOk();														// tối đa có thể thực hiện được
							message += orderDish.getDish().getDishName() + " chỉ thực hiện được tối đa " + min + " " + orderDish.getDish().getDishUnit();
							return message;																			//số lượng có thể đủ
						}
					}
				}
				
				//neu ko thieu nvl thi tang giam thoai mai
				if(orderDish.getStatus().getStatusId() == StatusConstant.STATUS_ORDER_DISH_ORDERED) {				// nếu là ordered tăng giảm đều được
					
					orderDish.setQuantity(dto.getQuantityOk());
					orderDish.setQuantityOk(dto.getQuantityOk());
					orderDish.setSumPrice(dto.getQuantityOk()*orderDish.getSellPrice());
					orderDish.setModifiedBy(dto.getModifiedBy());
					orderDish.setModifiedDate(Utils.getCurrentTime());
					orderDishRepo.save(orderDish);
					
				} else if (orderDish.getStatus().getStatusId() == StatusConstant.STATUS_ORDER_DISH_COMPLETED 
						|| orderDish.getStatus().getStatusId() == StatusConstant.STATUS_ORDER_DISH_PREPARATION) {	// nếu là completed hoặc prepare thì chỉ tăng số lượng (insert thêm lượng chênh)

					if(orderDish.getQuantityOk() >= dto.getQuantityOk()) {											// ko đc giảm
						return Constant.INPUT_WRONG;
					}else {
						OrderDishDto orderDishDto = getOrderDishById(dto.getOrderDishId());
						addQuantity = dto.getQuantityOk() - orderDishDto.getQuantityOk();							// tăng chỗ cộng thêm
						
						OrderDish orderDishNewDish = orderDishMapper.dtoToEntity(orderDishDto);
						orderDishNewDish.setQuantity(addQuantity);
						orderDishNewDish.setQuantityOk(addQuantity);
						orderDishNewDish.setQuantityCancel(0);
						orderDishNewDish.setSumPrice(orderDishNewDish.getSellPrice()*addQuantity);
						orderDishNewDish.setCreateBy(dto.getCreateBy());
						orderDishNewDish.setCreateDate(Utils.getCurrentTime());
						orderDishNewDish.setOrderDishId(null);
						orderDishRepo.save(orderDishNewDish);													// tạo mới ra thằng khác
						Long orderDishId = orderDishRepo.getLastestOrderDishId(orderDishDto.getOrderOrderId());
						orderDishNewDish.setOrderDishId(orderDishId);
						List<OrderDishOption> listOdo = null;
						OrderDishOption odo = null;
						Long orderDishOptionId = null;
						if(orderDishDto.getOrderDishOptions().size() != 0) {									// tạo mới các thằng topping
							listOdo = new ArrayList<OrderDishOption>();
							for (OrderDishOptionDtoNew odoNew : orderDishDto.getOrderDishOptions()) {
								odo = new OrderDishOption();
								orderDishOptionRepo.insertOrderDishOption(orderDishId, odoNew.getOptionId(), odoNew.getQuantity(), 
										odoNew.getSumPrice(), odoNew.getOptionPrice(), StatusConstant.STATUS_ORDER_DISH_OPTION_DONE);
								odo.setQuantity(odoNew.getQuantity());
								odo.setSumPrice(odoNew.getSumPrice());
								odo.setUnitPrice(odoNew.getOptionPrice());
								Status statusOdo = statusRepo.findById(StatusConstant.STATUS_ORDER_DISH_OPTION_DONE).get();
								Option option = optionRepo.findById(odoNew.getOptionId()).get();
								odo.setOption(option);
								odo.setStatus(statusOdo);
								odo.setOrderDish(orderDishNewDish);
								orderDishOptionId = orderDishOptionRepo.getLastestOrderDishOptionId(orderDishId);
								odo.setOrderDishOptionId(orderDishOptionId);
								listOdo.add(odo);
							}
						}
						orderDishNewDish.setOrderDishOptions(listOdo);
					}		
				}
				// sửa lại export trước đó
				Export export = null;																			// tăng số lượng
				Long exportId = exportRepo.getByOrderId(orderDish.getOrder().getOrderId());						// lấy ra export id theo order id
				export = exportRepo.findById(exportId).orElseThrow(
						() -> new NotFoundException("Not found Export: " + exportId));
				
				List<ExportMaterial> listExportMaterial = new ArrayList<ExportMaterial>();		
				Material material = null;
				Double remainNew = 0d, totalExportNew = 0d, quantityExportNew = 0d;
				for (Long materialId : map.keySet()) {															// upadate lại material, exportmaterial
					for (ExportMaterial exportMaterial : export.getExportMaterials()) {
						if(materialId == exportMaterial.getMaterial().getMaterialId()) {						// tìm material liên quan đến món ăn đó
							material = exportMaterial.getMaterial();											// lấy ra material đó
							if(checkIncrease) {																	// tăng số lượng
								remainNew = material.getRemain() - map.get(materialId);							// thay đổi remain
								totalExportNew = material.getTotalExport() + map.get(materialId);				// thay đổi totalexport
								quantityExportNew = exportMaterial.getQuantityExport() + map.get(materialId);	// thay đổi quantity ở exportmaterial
							}else {																				// giảm ở trường hợp ordered
								remainNew = material.getRemain() + map.get(materialId);							// thay đổi remain: cộng thêm
								totalExportNew = material.getTotalExport() - map.get(materialId);				// thay đổi totalexport: trừ đi
								quantityExportNew = exportMaterial.getQuantityExport() - map.get(materialId);	// thay đổi quantity ở exportmaterial
							}
							material.setTotalExport(totalExportNew);
							material.setRemain(remainNew);
							exportMaterial.setMaterial(material);
							exportMaterial.setQuantityExport(quantityExportNew);
							listExportMaterial.add(exportMaterial);											// lưu lại vào list
							break;
						}
					}
				}
				export.setExportMaterials(listExportMaterial);												// lưu lại vào export
				exportRepo.save(export);																	// lưu vào database
				// end sửa export
				orderRepo.updateStatusOrder(StatusConstant.STATUS_ORDER_ORDERED, dto.getOrderOrderId());
				SumQuantityAndPrice sum = getSumQtyAndPriceByOrder(dto.getOrderOrderId());	// cập nhật lại order
				orderService.updateOrderQuantity(sum.getSumQuantity(), sum.getSumPrice(), dto.getOrderOrderId());
				
				simpMessagingTemplate.convertAndSend("/topic/orderdetail/"+dto.getOrderOrderId(), orderService.getOrderById(dto.getOrderOrderId()));
				simpMessagingTemplate.convertAndSend("/topic/chef", orderService.getListDisplayChefScreen());
			} catch (NullPointerException e) {
				throw e;
			}
		}
		return "";
	}

	/**
	 * select sum quantity and price by order
	 */
	@Override
	public SumQuantityAndPrice getSumQtyAndPriceByOrder(Long orderId) {
		SumQuantityAndPrice sum = orderDishRepo.getSumQtyAndPrice(orderId, StatusConstant.STATUS_ORDER_DISH_CANCELED);
		return sum;
	}

	/**
	 * cập nhật lại topping
	 */
	@Override
	@Transactional
	public int updateToppingCommentOrderDish(OrderDishDto dto) {
		int result = 0;
		try {
			if(dto!= null && dto.getOrderDishOptions().size() != 0) {
				
				for (OrderDishOptionDtoNew orderDishOption : dto.getOrderDishOptions()) {								// nếu mà có topping thì hoặc là update topping, hoặc là thêm topping mới
					if(orderDishOption.getOrderDishOptionId() == 999999999 && orderDishOption.getQuantity() > 0) {		// nếu id gửi về là 99999999 và quantity > 0 thì là insert mới
						orderDishOptionService.insertOrderDishOption(orderDishOption, dto.getOrderDishId());
					}else if(orderDishOption.getOrderDishOptionId() == 999999999){										// nếu id gửi về là 99999999 thì ko làm gì cả
					}else {																								// nếu id gửi về ko phải là 99999999, tức là update cái topping cũ
						if(orderDishOption.getQuantity() == 0) {														// nếu cho về quantity = 0 thì xóa nó đi
							orderDishOptionRepo.deleteOrderDishOptionById(orderDishOption.getOrderDishOptionId());
						}else {																							// nếu ko thì update
							orderDishOptionService.updateQuantityOrderDishOption(orderDishOption);
						}
					}
				}
				result = orderDishRepo.updateToppingComment(dto.getComment(), dto.getSellPrice(), dto.getSumPrice(), dto.getOrderDishId());	// xong thì cập nhật lại comment và giá
				if(result == 1) { 																						// cập nhật lại order
					SumQuantityAndPrice sum = getSumQtyAndPriceByOrder(dto.getOrderOrderId());
					result = orderService.updateOrderQuantity(sum.getSumQuantity(), sum.getSumPrice(), dto.getOrderOrderId());
				}
			}else if(dto!= null && dto.getOrderDishOptions().size() == 0) {												// nếu ko gửi topping về thì là chỉ comment
				result = orderDishRepo.updateCommentOrderDish(dto.getComment(), dto.getOrderDishId());
			}
			simpMessagingTemplate.convertAndSend("/topic/orderdetail/"+dto.getOrderOrderId(), orderService.getOrderById(dto.getOrderOrderId()));
		} catch (Exception e) {
			return Constant.RETURN_ERROR_NULL;
		}
		
		return result;
	}

	/**
	 * select by id
	 */
	@Override
	public OrderDishDto getOrderDishById(Long orderDishId) {
		OrderDishDto dto = null;
		if(orderDishId != null) {
			OrderDish entity = orderDishRepo.findOrderDishById(orderDishId);
			if(entity != null) {
				dto = orderDishMapper.entityToDto(entity);
			}
		}
		return dto;
	}

	/**
	 * thay đổi nếu cancel món trong order
	 */
	@Override
	@Transactional
	public String updateCancelOrderDish(OrderDishDto dto) {
		try {
			
			OrderDish orderDish=orderDishRepo.findById(dto.getOrderDishId()).get();
			OrderDishCancelDto orderDishCancelDto = new OrderDishCancelDto();
			if(orderDish.getStatus().getStatusId() == StatusConstant.STATUS_ORDER_DISH_ORDERED
					|| orderDish.getStatus().getStatusId() == StatusConstant.STATUS_ORDER_DISH_CANCELED) {
				return Constant.STATUS_NOT_CHANGE;
			}
			if(orderDish.getQuantityOk() == null) {	
				return Constant.NO_DATA;
			}else {
				if(orderDish.getQuantityOk() != orderDish.getQuantity()) {									// lần thứ 2,3.. hủy món
					if(dto.getQuantityCancel() == orderDish.getQuantityOk()) {								// hủy hết
						if(orderDish.getOrderDishOptions().size() != 0) {
							orderDishOptionRepo.updateCancelOrderDishOption(StatusConstant.STATUS_ORDER_DISH_OPTION_CANCELED, dto.getOrderDishId());
						}
						orderDishCancelDto = new OrderDishCancelDto(null, dto.getQuantityCancel(), dto.getCommentCancel(), dto.getModifiedBy(), Utils.getCurrentTime(), dto.getOrderDishId());
						try {
							orderDishCancelService.insertCancel(orderDishCancelDto);						// thay đổi thì thêm bản ghi vào bảng cancel
						} catch (Exception e) {
							return Constant.STATUS_NOT_CHANGE;
						}
						dto.setQuantityOk(0);
						dto.setQuantityCancel(orderDish.getQuantity()); 									// hủy hết rồi thì = số lượng quantity ban đầu
						dto.setSumPrice(dto.getQuantityOk()*orderDish.getSellPrice());						// set lại tổng giá = 0
						
					}else if(dto.getQuantityCancel() < orderDish.getQuantityOk()) {							// hủy thêm 1 số
						orderDishCancelDto = new OrderDishCancelDto(null, dto.getQuantityCancel(), dto.getCommentCancel(), dto.getModifiedBy(), Utils.getCurrentTime(), dto.getOrderDishId());
						try {
							orderDishCancelService.insertCancel(orderDishCancelDto);						// thay đổi thì thêm bản ghi vào bảng cancel
						} catch (Exception e) {
							return Constant.STATUS_NOT_CHANGE;
						}
						dto.setQuantityOk(orderDish.getQuantityOk() - dto.getQuantityCancel());
						dto.setQuantityCancel(orderDish.getQuantityCancel() + dto.getQuantityCancel());
						dto.setSumPrice(dto.getQuantityOk()*orderDish.getSellPrice());
					}else {
						return Constant.CANCEL_NOT_MORE_THAN_OK;
					}
				}else {																						// lần đầu hủy món	
					if(dto.getQuantityCancel() == orderDish.getQuantityOk()) {								// hủy hết số còn lại
						if(dto.getOrderDishOptions().size() != 0) {
							orderDishOptionRepo.updateCancelOrderDishOption(StatusConstant.STATUS_ORDER_DISH_OPTION_CANCELED, dto.getOrderDishId());
						}
						orderDishCancelDto = new OrderDishCancelDto(null, dto.getQuantityCancel(), dto.getCommentCancel(), dto.getModifiedBy(), Utils.getCurrentTime(), dto.getOrderDishId());
						try {
							orderDishCancelService.insertCancel(orderDishCancelDto);						// thay đổi thì thêm bản ghi vào bảng cancel
						} catch (Exception e) {
							return Constant.STATUS_NOT_CHANGE;
						}
						dto.setQuantityOk(0);
						dto.setQuantityCancel(orderDish.getQuantity());										// tổng số quantity gọi ban đầu
						dto.setSumPrice(dto.getQuantityOk()*orderDish.getSellPrice());						// tính lại tổng giá	
					}else if(dto.getQuantityCancel() < orderDish.getQuantityOk()) {
						orderDishCancelDto = new OrderDishCancelDto(null, dto.getQuantityCancel(), dto.getCommentCancel(), dto.getModifiedBy(), Utils.getCurrentTime(), dto.getOrderDishId());
						try {
							orderDishCancelService.insertCancel(orderDishCancelDto);						// thay đổi thì thêm bản ghi vào bảng cancel
						} catch (Exception e) {
							return Constant.STATUS_NOT_CHANGE;
						}
						dto.setQuantityOk(orderDish.getQuantityOk() - dto.getQuantityCancel());
						dto.setQuantityCancel(dto.getQuantityCancel() + orderDish.getQuantityCancel());
						dto.setSumPrice(dto.getQuantityOk()*orderDish.getSellPrice());
					}else {
						return Constant.CANCEL_NOT_MORE_THAN_OK;
					}
				}
			}
			orderDish.setQuantityOk(dto.getQuantityOk());
			orderDish.setSumPrice(dto.getSumPrice());
			orderDish.setQuantityCancel(dto.getQuantityCancel());
			orderDish.setModifiedBy(dto.getModifiedBy());
			orderDish.setModifiedDate(Utils.getCurrentTime());
			if(dto.getQuantityCancel() == orderDish.getQuantity()) {
				Status status = statusRepo.findById(StatusConstant.STATUS_ORDER_DISH_CANCELED)
						.orElseThrow(()-> new NotFoundException("Not found Status: "+StatusConstant.STATUS_ORDER_DISH_CANCELED));
				orderDish.setStatus(status);
			}else {
				Status status = statusRepo.findById(orderDish.getStatus().getStatusId()).get();
				orderDish.setStatus(status);
			}
			orderDishRepo.save(orderDish);
			
			SumQuantityAndPrice sum = getSumQtyAndPriceByOrder(dto.getOrderOrderId());								// cập nhật lại số lượng và giá trong order
			orderService.updateOrderQuantity(sum.getSumQuantity(), sum.getSumPrice(), dto.getOrderOrderId());
			
			simpMessagingTemplate.convertAndSend("/topic/orderdetail/"+dto.getOrderOrderId(), orderService.getOrderById(dto.getOrderOrderId()));		// socket
				
		} catch (NullPointerException e) {
			return Constant.NULL;
		}
		
		return Constant.CHANGE_SUCCESS;
	}

	
	/**
	 * đếm số món chưa complete
	 */
	@Override
	public int getCountStatusOrderDish(Long orderId, Long statusId) {
		Integer count = 100000;
		if(orderId != null) {
			count = orderDishRepo.getCountStatusOrderDish(orderId, statusId);
			if(count == null) {
				count = 100000;
			}	
		}
		return count;
	}
	
	/**
	 * hiển thị danh sách lên màn hình trả món, chỉ hiển thị các món đã hoàn thành
	 */
	@Override
	public List<OrderDishDto> getCanReturnByOrderId(Long orderId) {
		
		List<OrderDishDto> listDto = null;
		if(orderId != null) {
			List<OrderDish> listOrderDish = orderDishRepo.getCanReturnByOrderId(StatusConstant.STATUS_ORDER_DISH_COMPLETED, orderId);
			listDto = listOrderDish.stream().map(orderDishMapper::entityToDto)
					.collect(Collectors.toList());	
			
			for (int i = 0; i < listOrderDish.size(); i++) {														// topping
				List<OrderDishOptionDtoNew> listOrderDishOption = new ArrayList<OrderDishOptionDtoNew>();
				if(listDto.get(i).getOrderDishOptions() != null && listDto.get(i).getOrderDishOptions().size() != 0) {
					
					listOrderDishOption = listOrderDish.get(i).getOrderDishOptions()
					.stream().map(orderDishOptionMapper::entityToDto).collect(Collectors.toList());	;
				}
				listDto.get(i).setOrderDishOptions(listOrderDishOption);
			}
		}
		return listDto;
	}

	@Override
	@Transactional
	public String updateReturnDish(List<OrderDishRequest> listOdr) {
		
		try {
			Export export = null;
			Map<Long, Double> map = new HashMap<Long, Double>();
			boolean checkReturnOk;
			boolean checkMaterial=false;
			DishInOrderDish dishIn = null;
			Map<DishInOrderDish, List<GetQuantifierMaterial>> mapDish = new HashMap<DishInOrderDish, List<GetQuantifierMaterial>>();
			List<GetQuantifierMaterial> listQuantifier = null;
			if(listOdr != null && listOdr.size() != 0) {
				OrderDish orderDish = null;
				for (OrderDishRequest orderDishRequest : listOdr) {
					checkReturnOk = false;
					if(orderDishRequest.getQuantityReturn() > 0) {
						orderDish = new OrderDish();
						orderDish = orderDishRepo.findById(orderDishRequest.getOrderDishId()).get();
						if(orderDish.getQuantityOk() >= orderDishRequest.getQuantityReturn()) {									// số lượng trả phải nhỏ hơn số lượng thực tế gọi
							orderDish.setQuantity(orderDish.getQuantity()-orderDishRequest.getQuantityReturn());
							orderDish.setQuantityOk(orderDish.getQuantityOk()-orderDishRequest.getQuantityReturn());
							orderDish.setModifiedBy(orderDishRequest.getModifiedBy());
							orderDish.setModifiedDate(Utils.getCurrentTime());
							orderDish.setSumPrice(orderDish.getQuantityOk()*orderDish.getSellPrice());
							orderDishRepo.save(orderDish);
							checkReturnOk=true;																		// món này có trả lại
						}else {
							return Constant.QUANTITY_RETURN;
						}
					}
					if(checkReturnOk) {																				// món này có trả món
						dishIn = new DishInOrderDish();
						dishIn.setDishId(orderDish.getDish().getDishId());
						dishIn.setQuantity(orderDishRequest.getQuantityReturn());
						dishIn.setOrderDishId(orderDishRequest.getOrderDishId());
						listQuantifier = new ArrayList<GetQuantifierMaterial>();
						listQuantifier = orderRepo.getListQuantifierMaterialByDish(dishIn.getDishId());
						if(listQuantifier.size()!= 0) {
							mapDish.put(dishIn, listQuantifier);													// lưu vào map: dish và list material theo dish
							checkMaterial = true;
						}	
					}
				}
				if(checkMaterial) {
					map = TestCheckKho.testKho(mapDish);															// phân tách ra theo material và quantity
					Long exportId = exportRepo.getByOrderId(listOdr.get(0).getOrderId());							// lấy ra export id theo order id
					export = exportRepo.findById(exportId).orElseThrow(
							() -> new NotFoundException("Not found Export: " + exportId));
					Material material = null;
					List<ExportMaterial> exportMaterials = new ArrayList<ExportMaterial>();
					Double remainNew = 0d, totalExportNew = 0d, quantityExportNew = 0d;
					for (Long materialId : map.keySet()) {
						for (ExportMaterial exportMaterial : export.getExportMaterials()) {
							if(materialId == exportMaterial.getMaterial().getMaterialId()) {						// tìm material liên quan đến món ăn đó
								material = exportMaterial.getMaterial();											// lấy ra material đó
								
								remainNew = material.getRemain() + map.get(materialId);								// thay đổi remain
								totalExportNew = material.getTotalExport() - map.get(materialId);					// thay đổi totalexport
								quantityExportNew = exportMaterial.getQuantityExport() - map.get(materialId);		// thay đổi quantity ở exportmaterial
									
								material.setTotalExport(totalExportNew);
								material.setRemain(remainNew);
								exportMaterial.setMaterial(material);
								exportMaterial.setQuantityExport(quantityExportNew);
								exportMaterials.add(exportMaterial);												// lưu lại vào list
								break;
							}
						}
					}
					export.setExportMaterials(exportMaterials);													// lưu lại vào export
					exportRepo.save(export);																	// lưu vào database
				}
				
				Long orderId = listOdr.get(0).getOrderId();
				if(orderId != null) {
					SumQuantityAndPrice sum = getSumQtyAndPriceByOrder(orderId);									// cập nhật lại số lượng và giá trong order
					orderService.updateOrderQuantity(sum.getSumQuantity(), sum.getSumPrice(), orderId);

					simpMessagingTemplate.convertAndSend("/topic/orderdetail/" + orderId, orderService.getOrderById(orderId));		// socket
				}
			}
			
		}catch (Exception e) {
			throw e;
		}
		
		return Constant.CHANGE_SUCCESS;
	}

	/*
	 * đổi tất cả order theo món
	 */
	@Override
	@Transactional
	public int updateStatusByDish(OrderDishChefRequest request) {
		int result = 0;
		try {
//			Long statusCurrent = orderDishRepo.getStatusByOrderDishId(request.getOrderDishId());										// tìm trạng thái hiện tại của món
			if(request.getStatusId() != null) {
				int count = 0;
				if(request.getStatusId() == StatusConstant.STATUS_ORDER_DISH_PREPARATION) {												// bấm xác nhận thực hiện
					result = orderDishRepo.updateStatusByDish(StatusConstant.STATUS_ORDER_DISH_PREPARATION, request.getDishId());
					if(result == 1) {
						List<Long> listOrderId = orderDishRepo.getOrderIdByDishId(request.getDishId());
						for (Long orderId : listOrderId) {																				// tìm các món liên quan dishid đó, 
							count = orderDishRepo.getCountStatusOrderDish(orderId, StatusConstant.STATUS_ORDER_DISH_ORDERED);			//để chuyển trạng thái cả order nếu ko còn món nào
							if(count == 0) {
								result = orderRepo.updateStatusOrder(StatusConstant.STATUS_ORDER_PREPARATION, orderId);
							}
						}
					}
				}else if(request.getStatusId() == StatusConstant.STATUS_ORDER_DISH_COMPLETED) {											// bấm đã hoàn thành món đó
					result = orderDishRepo.updateStatusByDish(StatusConstant.STATUS_ORDER_DISH_COMPLETED, request.getDishId());
					if(result == 1) {
						List<Long> listOrderId = orderDishRepo.getOrderIdByDishId(request.getDishId());
						for (Long orderId : listOrderId) {																				// tìm các món liên quan dishid đó, 
							count = orderDishRepo.getCountStatusOrderDish(orderId, StatusConstant.STATUS_ORDER_DISH_PREPARATION);		//để chuyển trạng thái cả order nếu ko còn món nào
							if(count == 0) {
								result = orderRepo.updateStatusOrder(StatusConstant.STATUS_ORDER_COMPLETED, orderId);
							}
						}
					}
				}
			}
			simpMessagingTemplate.convertAndSend("/topic/chef", orderService.getListDisplayChefScreen());
		} catch (Exception e) {
			throw e;
		}
		
		return result;
	}

	/*
	 * đổi theo từng order, món
	 */
	@Override
	@Transactional
	public OrderDishChef updateStatusByDishAndOrder(OrderDishChefRequest request) {
		OrderDishChef orderdishChef = null;
		int result = 0;
		try {
			if(request.getOrderDishId() != null) {
				Long orderId = orderDishRepo.getOrderByOrderDishId(request.getOrderDishId());
				if(request.getStatusId() != null) {
					int count = 0;
					if(request.getStatusId() == StatusConstant.STATUS_ORDER_DISH_PREPARATION) {												// xác nhận thực hiện món ăn
						result = orderDishRepo.updateStatusByDishAndOrder(StatusConstant.STATUS_ORDER_DISH_PREPARATION, request.getOrderDishId());
						count = getCountStatusOrderDish(orderId, StatusConstant.STATUS_ORDER_DISH_ORDERED);
						if(count == 0) {
							result = orderRepo.updateStatusOrder(StatusConstant.STATUS_ORDER_PREPARATION, orderId);
						}
					}else if(request.getStatusId() == StatusConstant.STATUS_ORDER_DISH_COMPLETED) {											// xác nhận thực hiện món ăn xong
						result = orderDishRepo.updateStatusByDishAndOrder(StatusConstant.STATUS_ORDER_DISH_COMPLETED, request.getOrderDishId());
						count = getCountStatusOrderDish(orderId, StatusConstant.STATUS_ORDER_DISH_PREPARATION);
						if(count == 0) {
							result = orderRepo.updateStatusOrder(StatusConstant.STATUS_ORDER_COMPLETED, orderId);
						}
					}
				}
			}
			if(result != 0) {																				// upadate thành công
				OrderDish entity = orderDishRepo.findById(request.getOrderDishId())
						.orElseThrow(()-> new NotFoundException("Not found OrderDish: " + request.getOrderDishId()));
				orderdishChef = orderDishMapper.entityToChef(entity);	
			}
			
			
			simpMessagingTemplate.convertAndSend("/topic/chef", orderService.getListDisplayChefScreen());
		} catch (Exception e) {
			throw e;
		}
		
		return orderdishChef;
	}


}
