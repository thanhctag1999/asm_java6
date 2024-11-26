angular.module('myApp', ['ngRoute'])
.config(function($routeProvider) {
    $routeProvider
        .when("/product", {
            templateUrl: "/Admin/product.html",
            controller: 'ProductController'
        })
        .when("/productList", {
            templateUrl: "/Admin/productList.html"
        })
        .when("/user", {
            templateUrl: "/Admin/user.html",
        })
        .when("/userList", {
            templateUrl: "/Admin/userList.html",
        })
        .when("/home", {
            templateUrl: "/Admin/home.html",
        })
        .when("/discount", {
            templateUrl: "/Admin/discount.html",
        })
        .when("/discountList", {
            templateUrl: "/Admin/discountList.html",
        })
        .when("/order", {
            templateUrl: "/Admin/order.html",
        })
        .when("/orderDetail", {
            templateUrl: "/Admin/orderDetail.html",
        })
        .otherwise({
            redirectTo: "/home"
        });
})
.controller('MainController', function($scope) {
    $scope.pageTitle = "Trang chủ";
    $scope.isExpanded = false;
    $scope.isDropdownOpen = false; // Đặt giá trị mặc định cho biến này

    $scope.toggleSidebar = function() {
        $scope.isExpanded = !$scope.isExpanded;
    };

    $scope.setPage = function(title) {
        $scope.pageTitle = title;
        $scope.isDropdownOpen = false; // Đóng dropdown khi chuyển trang
    };

    $scope.toggleDropdown = function() {
        $scope.isDropdownOpen = !$scope.isDropdownOpen; // Đảo ngược giá trị
    };

    // Đóng dropdown khi click ra ngoài
    window.onclick = function(event) {
        if (!event.target.matches('.avatar') && !event.target.matches('.dropdown-menu') && !event.target.matches('.dropdown-item')) {
            $scope.$apply(function() {
                $scope.isDropdownOpen = false;
            });
        }
    };
});

 // Hàm để hiển thị bảng được chọn
 function showTable(tableId) {
    // Ẩn tất cả các bảng
    document.querySelectorAll('.table-container').forEach(function (table) {
        table.style.display = 'none';
    });
    // Hiển thị bảng được chọn
    document.getElementById(tableId).style.display = 'block';
}
// Khởi tạo DataTables cho tất cả các bảng
$(document).ready(function () {
    $('table').DataTable();
});



