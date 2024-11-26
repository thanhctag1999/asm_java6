const app = angular.module("myApp", ["ngRoute"]);
app.config(function ($routeProvider) {
  $routeProvider
    .when("/Admin/home", {
      templateUrl: "/templates/Admin/AdminHome",
    })
    .when("/Admin/products/add", {
      templateUrl: "/templates/Admin/Product",
    })

    .when("/templates/Admin/list", {
      templateUrl: "/templates/Admin/ProductList",
    });
});
