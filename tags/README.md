POST /tag/RegisterUser :
{
  "emailid": "rudra.hublimath@gmail.com",
  "name": "Rudra",
  "pwd": "admin",
  "role": "ADMIN"
}


/cart/addToCart
{
  "filefullpath": "C:\\work\\D_001.jpg",
  "name": "D_001",
  "price": 100,
  "size": "small"
}


/cart/checkout :

{
  "address1": "c-704,Gulmohar renaissance,baif road,",
  "address2": "wagholi , pune , MH , india -4130002 ",
  "phoneno": "8805471613",
  "prod_backinfo": "1234512345",
  "prod_backnm": "Hrushi",
  "prod_frontdesign": "Panja",
  "prod_frontnm": "MOTI",
  "status": "received"
}


PUT /cart/updateProductQuantity  :

  {
    "product": {
      "name": "D_001",
      "price": 100,
      "size": "small",
      "filefullpath": "C:\\work\\D_001.jpg"
    },
    "quantity": 3
  }

GET /secured/order/sendmail
{
  "mailTO": "rudra.hublimath@gmail.com",
  "subject": "BowBowtags.com - order confirmation",
  "textinfo": "Your order is confirmed. Total amount is 300/- RS"
}

##########


POST /secured/api/fileupload      		>> uploadFile
GET /secured/billdetail/get/{id}    	>> getbilldetail
GET /secured/orderdetail/get/{status}   >> getorderByStatus
PUT /secured/orderdetail/put/{id}		>> updateasdelivered
DELETE /secured/products/{filename}		>> delete
GET /secured/order/sendmail				>> sendMailBill
GET /showme/all							>> viewallproducts


POST /secured/api/PhotoGallery
DELETE /secured/api/PhotoGallery/{filename}
GET /showme/PhotoGallery/all

GET /tag/LoginCheck/{emailid}/{password} >> loginUser    (Token generates with this)
POST /tag/RegisterUser					>> signupUser


POST /cart/addToCart					>> addToCart
POST /cart/checkout/{name}/{emailid}	>> checkout
DELETE /cart/deleteFromCart/{name}		>> deleteFromCartRest
GET /cart/productsAmount				>> getProductAmountInCart
GET /cart/productsCount					>> getProductCountInCart
GET /cart/productsOrderList				>> getProductsOrderList
PUT /cart/updateProductQuantity			>> updateProductQuantity


GET /forgotpwd/sendMail/{emailid}/		>> ForgotPassword

##########

SELECT * FROM tags.user;
SELECT * FROM tags.admin_products;
SELECT * FROM tags.photogallery;

SELECT * FROM tags.products;
SELECT * FROM tags.shopping_cart;
SELECT * FROM tags.product_order;

##########
Header >> For Token
Key   : Authorization
Value : Token eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJSdWRyYSIsInVzZXJJZCI6IjEiLCJyb2xlIjoiQURNSU4ifQ.qT_gqV54vJrmOBxLObGaibgqDv0knHkDthvSOrWpOJOSVmNAi_dAdHKKBUltJ7gf5sTIh5Uadub9Jmn-LBIMuw
