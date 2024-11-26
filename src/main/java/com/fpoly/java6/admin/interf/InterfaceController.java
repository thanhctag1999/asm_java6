package com.fpoly.java6.admin.interf;


import java.awt.Image;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.java6.admin.repository.BrandRepository;
import com.fpoly.java6.admin.repository.ColorRepository;
import com.fpoly.java6.admin.repository.DiscountRepository;
import com.fpoly.java6.admin.repository.SizeRepository;
import com.fpoly.java6.admin.service.AccountServiceAdmin;
import com.fpoly.java6.admin.service.BrandServiceAdmin;
import com.fpoly.java6.admin.service.ColorServiceAdmin;
import com.fpoly.java6.admin.service.DiscountServiceAdmin;
import com.fpoly.java6.admin.service.OrderServiceAdmin;
import com.fpoly.java6.admin.service.ProductVariantServiceAdmin;
import com.fpoly.java6.admin.service.SizeServiceAdmin;
import com.fpoly.java6.admin.service.TypeServiceAdmin;
import com.fpoly.java6.entities.Account;
import com.fpoly.java6.entities.Brand;
import com.fpoly.java6.entities.Color;
import com.fpoly.java6.entities.Discount;
import com.fpoly.java6.entities.Order;
import com.fpoly.java6.entities.Product;
import com.fpoly.java6.entities.Size;
import com.fpoly.java6.entities.Type;
import com.fpoly.java6.entities.Variant;

@Controller
@RequestMapping("/Admin")
public class InterfaceController {
	
	 	@Autowired
	    private ProductVariantServiceAdmin productService;
	 	@Autowired
	    private BrandServiceAdmin brandService;

	    @Autowired
	    private TypeServiceAdmin typeService;
	    
	    @Autowired
	    private SizeServiceAdmin sizeService;
	    
	    @Autowired
	    private SizeRepository sizeRepository;

	    @Autowired
	    private ColorServiceAdmin colorService;
	    
	    @Autowired
	    private ColorRepository colorRepository;
	    
		@Autowired
		private ProductVariantServiceAdmin productVariantService;
		
		@Autowired
		private AccountServiceAdmin accountService;
		
		@Autowired
		private BrandRepository brandRepository;
		
		@Autowired
		private DiscountServiceAdmin discountService;
		
		@Autowired
		private DiscountRepository discountRepository;
		
		@Autowired
		private OrderServiceAdmin orderService;


    
	    @GetMapping("/products/add")
	    public String showProduct( Model model) {
	        List<Brand> brands = brandService.getAllBrands();
	        List<Type> types = typeService.getAllTypes();
	        List<Size> sizes = sizeService.getAllSizes();
	        List<Color> colors = colorService.getAllColors();
	        
	        // Thêm dữ liệu vào Model để truyền sang view
	        model.addAttribute("sizes", sizes);
	        model.addAttribute("colors", colors);
	        model.addAttribute("brands", brands);
	        model.addAttribute("types", types);
	        return "Admin/Product"; // Chuyển hướng đến form thêm/sửa sản phẩm
	    }

	    @GetMapping(value = "/products/edit/{id}", produces = MediaType.TEXT_HTML_VALUE)
	    public String showProductForm(@PathVariable int id, Model model) {
	        Product product = productVariantService.findById(id);

	        // Kiểm tra nếu không tìm thấy sản phẩm
	        if (product == null) {
	            model.addAttribute("error", "Không tìm thấy sản phẩm với ID: " + id);
	            return "Admin/Product"; // Hoặc trả về một trang lỗi khác nếu cần
	        }

	        // Nếu có sản phẩm, thêm thông tin vào model
	        model.addAttribute("product", product);
	        System.out.println("Product ID: " + product.getId());
	        System.out.println("Brand ID: " + (product.getBrand() != null ? product.getBrand().getId() : "Brand is null"));
	        System.out.println("Type ID: " + (product.getType() != null ? product.getType().getId() : "Type is null"));

	        // Thêm danh sách brand và type
	        List<Size> sizes = sizeService.getAllSizes();
	        List<Color> colors = colorService.getAllColors();
	        List<Brand> brands = brandService.getAllBrands();
	        List<Type> types = typeService.getAllTypes();
	  
	        model.addAttribute("sizes", sizes);
	        model.addAttribute("colors", colors);
	        model.addAttribute("brands", brands);
	        model.addAttribute("types", types);

	        return "Admin/Product";
	    }

	    @GetMapping("/products/list")
	    public String showProductList(Model model) {
	        List<Product> products = productService.getAllProducts();
	        List<Color> colors = colorService.getAllColors();
	        List<Size> sizes = sizeService.getAllSizes();
	        List<Variant> variants = productService.getAllVariants();
	        List<Brand> brands = brandService.getAllBrands();  // Thêm brands
	        List<Type> types = typeService.getAllTypes();      // Thêm types

	        model.addAttribute("products", products);
	        model.addAttribute("colors", colors);
	        model.addAttribute("sizes", sizes);
	        model.addAttribute("variants", variants);
	        model.addAttribute("brands", brands);              // Truyền brands vào model
	        model.addAttribute("types", types);   

	        return "Admin/ProductList"; // Tên file HTML là productList.html
	    }
 
	    @GetMapping("/accounts/add")
	    public String showAccount( Model model) {
	       
	        return "Admin/Account"; // Chuyển hướng đến form thêm/sửa sản phẩm
	    }

	    @GetMapping("/accounts/list")
	    public String showAccountList(Model model, Principal principal) {
	        List<Account> accounts = accountService.findAllAccounts();
	        int loggedInUserRole = getLoggedInUserRole(principal);
	        model.addAttribute("loggedInUserRole", loggedInUserRole);
	        model.addAttribute("accounts", accounts);
	        return "Admin/AccountList"; // trả về tên view
	    }

	    private int getLoggedInUserRole(Principal principal) {
	        if (principal != null) {
	            String username = principal.getName();
	            Account loggedInAccount = accountService.findByUsername(username);
	            return loggedInAccount != null ? loggedInAccount.getRole() : -1;
	        }
	        return -1;
	    }



	    @GetMapping(value = "/accounts/edit/{id}", produces = MediaType.TEXT_HTML_VALUE)
	    public String showAccountForm(@PathVariable int id, Model model) {
	        Account account = accountService.findById(id);

	        // Kiểm tra nếu không tìm thấy tài khoản
	        if (account == null) {
	            model.addAttribute("error", "Không tìm thấy tài khoản với ID: " + id);
	            return "Admin/Account"; // Hoặc trả về một trang lỗi khác nếu cần
	        }

	        // Nếu có tài khoản, thêm thông tin vào model
	        model.addAttribute("account", account);


	        return "Admin/Account";
	    }
	    
	    @GetMapping("/brands/add")
	    public String showBrands(Model model) {
	        // Lấy toàn bộ danh sách thương hiệu từ cơ sở dữ liệu
	        List<Brand> brands = brandRepository.findAll(); 

	        // Truyền danh sách vào model để sử dụng trên giao diện
	        model.addAttribute("brands", brands);

	        // Chuyển đến trang Admin/Brand
	        return "Admin/Brand";
	    }
	    @GetMapping(value = "/brands/edit/{id}", produces = MediaType.TEXT_HTML_VALUE)
	    public String showBrandForm(@PathVariable int id, Model model) {
	        // Tìm brand dựa vào ID
	        Brand brand = brandService.findById(id);

	        // Kiểm tra nếu không tìm thấy thương hiệu
	        if (brand == null) {
	            model.addAttribute("error", "Không tìm thấy thương hiệu với ID: " + id);
	            return "Admin/Brand"; // Hoặc trả về một trang lỗi khác nếu cần
	        }

	        // Nếu tìm thấy thương hiệu, thêm thông tin vào model
	        model.addAttribute("brand", brand);

	        return "Admin/Brand"; // Trang giao diện chỉnh sửa thương hiệu
	    }
	    
	    @GetMapping("/colors/add")
	    public String showColors(Model model) {
	        // Lấy toàn bộ danh sách màu sắc từ cơ sở dữ liệu
	        List<Color> colors = colorRepository.findAll();

	        // Truyền danh sách vào model để sử dụng trên giao diện
	        model.addAttribute("colors", colors);

	        // Chuyển đến trang Admin/Color
	        return "Admin/Color";
	    }

	    @GetMapping(value = "/colors/edit/{id}", produces = MediaType.TEXT_HTML_VALUE)
	    public String showColorForm(@PathVariable int id, Model model) {
	        // Tìm color dựa vào ID
	        Color color = colorService.findByIdOrNull(id);

	        // Kiểm tra nếu không tìm thấy màu sắc
	        if (color == null) {
	            model.addAttribute("error", "Không tìm thấy màu sắc với ID: " + id);
	            return "Admin/Color"; // Hoặc trả về một trang lỗi khác nếu cần
	        }

	        // Nếu tìm thấy màu sắc, thêm thông tin vào model
	        model.addAttribute("color", color);

	        // Chuyển đến trang Admin/Color để chỉnh sửa màu sắc
	        return "Admin/Color"; // Trang giao diện chỉnh sửa màu sắc
	    }
	    
	    // Hiển thị tất cả kích thước
	    @GetMapping("/sizes/add")
	    public String showSizes(Model model) {
	        // Lấy toàn bộ danh sách kích thước từ cơ sở dữ liệu
	        List<Size> sizes = sizeRepository.findAll();

	        // Truyền danh sách vào model để sử dụng trên giao diện
	        model.addAttribute("sizes", sizes);

	        // Chuyển đến trang Admin/Size
	        return "Admin/Size";
	    }

	    // Hiển thị form chỉnh sửa kích thước
	    @GetMapping(value = "/sizes/edit/{id}", produces = MediaType.TEXT_HTML_VALUE)
	    public String showSizeForm(@PathVariable int id, Model model) {
	        // Tìm kích thước theo ID
	        Size size = sizeService.findByIdOrNull(id);

	        // Kiểm tra nếu không tìm thấy kích thước
	        if (size == null) {
	            model.addAttribute("error", "Không tìm thấy kích thước với ID: " + id);
	            return "Admin/Size"; // Hoặc trả về một trang lỗi khác nếu cần
	        }

	        // Nếu tìm thấy kích thước, thêm thông tin vào model
	        model.addAttribute("size", size);

	        // Chuyển đến trang Admin/Size để chỉnh sửa kích thước
	        return "Admin/Size"; // Trang giao diện chỉnh sửa kích thước
	    }

	    // Hiển thị tất cả loại
	    @GetMapping("/types/add")
	    public String showTypes(Model model) {
	        // Lấy toàn bộ danh sách loại từ cơ sở dữ liệu
	        List<Type> types = typeService.findAll();

	        // Truyền danh sách vào model để sử dụng trên giao diện
	        model.addAttribute("types", types);

	        // Chuyển đến trang Admin/Type
	        return "Admin/Type"; // Trang hiển thị danh sách các loại
	    }

	    // Hiển thị form chỉnh sửa loại
	    @GetMapping(value = "/types/edit/{id}", produces = MediaType.TEXT_HTML_VALUE)
	    public String showTypeForm(@PathVariable int id, Model model) {
	        // Tìm loại theo ID
	        Type type = typeService.findByIdOrNull(id);

	        // Kiểm tra nếu không tìm thấy loại
	        if (type == null) {
	            model.addAttribute("error", "Không tìm thấy loại với ID: " + id);
	            return "Admin/Type"; // Trả về trang lỗi hoặc trang danh sách loại
	        }

	        // Nếu tìm thấy loại, thêm thông tin vào model
	        model.addAttribute("type", type);

	        // Chuyển đến trang Admin/Type để chỉnh sửa loại
	        return "Admin/Type"; // Trang giao diện chỉnh sửa loại
	    }
	    
	    @GetMapping("/discounts/add")
	    public String showDiscounts(Model model) {
	        // Lấy toàn bộ danh sách mã giảm giá từ cơ sở dữ liệu
	        List<Discount> discounts = discountService.findAll();

	        // Truyền danh sách vào model để sử dụng trên giao diện
	        model.addAttribute("discounts", discounts);

	        // Chuyển đến trang Admin/Discounts
	        return "Admin/Discount"; // Trang hiển thị danh sách các mã giảm giá
	    }
	    
	    @GetMapping(value = "/discounts/edit/{discountId}", produces = MediaType.TEXT_HTML_VALUE)
	    public String showDiscountForm(@PathVariable Integer discountId, Model model) {
	        // Tìm mã giảm giá theo ID
	        Discount discount = discountService.findById(discountId);

	        // Kiểm tra nếu không tìm thấy mã giảm giá
	        if (discount == null) {
	            model.addAttribute("error", "Không tìm thấy mã giảm giá với ID: " + discountId);
	            return "Admin/Discounts"; // Trả về trang lỗi hoặc trang danh sách mã giảm giá
	        }

	        // Nếu tìm thấy mã giảm giá, thêm thông tin vào model
	        model.addAttribute("discount", discount);

	        // Chuyển đến trang Admin/Discounts để chỉnh sửa mã giảm giá
	        return "Admin/Discount"; // Trang giao diện chỉnh sửa mã giảm giá
	    }

	    @GetMapping(value ="/orders", produces = MediaType.TEXT_HTML_VALUE)
	    public String getAllOrders(Model model) {
	        List<Order> orders = orderService.findAll(); // Lấy tất cả đơn hàng từ service
	        model.addAttribute("orders", orders); // Truyền danh sách đơn hàng vào model
	        return "Admin/Order"; // Trả về view 'ordersList.html'
	    }


}
