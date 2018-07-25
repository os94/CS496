<?php 
session_start();
 $connect = mysqli_connect("localhost", "root", "apmsetup", "mydatabase");  
 if(isset($_POST["add_to_cart"]))  
 {  

if (!function_exists('array_column')) {
function array_column($input = null, $columnKey = null, $indexKey = null)
{
    $argc = func_num_args();
    $params = func_get_args();
    if ($argc < 2) {
        trigger_error("array_column() expects at least 2 parameters, {$argc} given", E_USER_WARNING);
        return null;
    }
    if (!is_array($params[0])) {
        trigger_error(
            'array_column() expects parameter 1 to be array, ' . gettype($params[0]) . ' given',
            E_USER_WARNING
        );
        return null;
    }
    if (!is_int($params[1])
        && !is_float($params[1])
        && !is_string($params[1])
        && $params[1] !== null
        && !(is_object($params[1]) && method_exists($params[1], '__toString'))
    ) {
        trigger_error('array_column(): The column key should be either a string or an integer', E_USER_WARNING);
        return false;
    }
    if (isset($params[2])
        && !is_int($params[2])
        && !is_float($params[2])
        && !is_string($params[2])
        && !(is_object($params[2]) && method_exists($params[2], '__toString'))
    ) {
        trigger_error('array_column(): The index key should be either a string or an integer', E_USER_WARNING);
        return false;
    }
    $paramsInput = $params[0];
    $paramsColumnKey = ($params[1] !== null) ? (string) $params[1] : null;
    $paramsIndexKey = null;
    if (isset($params[2])) {
        if (is_float($params[2]) || is_int($params[2])) {
            $paramsIndexKey = (int) $params[2];
        } else {
            $paramsIndexKey = (string) $params[2];
        }
    }

    $resultArray = array();

    foreach ($paramsInput as $row) {
        $key = $value = null;
        $keySet = $valueSet = false;

        if ($paramsIndexKey !== null && array_key_exists($paramsIndexKey, $row)) {
            $keySet = true;
            $key = (string) $row[$paramsIndexKey];
        }

        if ($paramsColumnKey === null) {
            $valueSet = true;
            $value = $row;
        } elseif (is_array($row) && array_key_exists($paramsColumnKey, $row)) {
            $valueSet = true;
            $value = $row[$paramsColumnKey];
        }

        if ($valueSet) {
            if ($keySet) {
                $resultArray[$key] = $value;
            } else {
                $resultArray[] = $value;
            }
        }

    }

    return $resultArray;
}

}



      if(isset($_SESSION["shopping_cart"]))  
      {  
           $item_array_id = array_column($_SESSION["shopping_cart"], "item_id");  
           if(!in_array($_GET["id"], $item_array_id))  
           {  
                $count = count($_SESSION["shopping_cart"]);  
                $item_array = array(  
                     'item_id'=>$_GET["id"],  
                     'item_name'=>$_POST["hidden_name"],  
                     'item_price'=>$_POST["hidden_price"],  
                     'item_quantity'=>$_POST["quantity"]  
                );  
                $_SESSION["shopping_cart"][$count] = $item_array;
           }  
           else  
           {  
                echo '<script>alert("Item Already Added")</script>';  
                echo '<script>window.location="index2.php"</script>';  
           }  
      }  
      else  
      {  
           $item_array = array(  
                'item_id'=>$_GET["id"],  
                'item_name'=>$_POST["hidden_name"],  
                'item_price' =>$_POST["hidden_price"],  
                'item_quantity' =>  $_POST["quantity"]  
           );  
           $_SESSION["shopping_cart"][0] = $item_array;  
      }  
 }  
 if(isset($_GET["action"]))  
 {  
      if($_GET["action"] == "delete")  
      {

           foreach($_SESSION["shopping_cart"] as $keys => $values)  
           {  
                if($values["item_id"] == $_GET["id"])  
                {  
                     unset($_SESSION["shopping_cart"][$keys]);  
                     echo '<script>alert("Item Removed")</script>';  
                     echo '<script>window.location="index2.php"</script>';  
                }  
           }  
      }  
 }  
 ?>  
 <!DOCTYPE html>  
 <html>  
      <head>  
           <title>GDVBS_GDVBS | Take Home some GOODVIBES</title>  
           <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>  
           <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
           <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />  
           <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>  
           <style>
             .img{
              height: 500px;
              width: 500px;
              background-size:cover;
              margin:0 auto;
              padding:0 auto;
             }
              .img:hover{
              height: 500px;
              width: 500px;
              background-size:cover;
              margin:0 auto;
              padding:0 auto;
              opacity:0.5;
             }
            .text{
              align-content: center;
              margin:0 auto;
              display:table;
              animation:fading 5s
            }
            @keyframes animatebottom{
              from{bottom:-100px;opacity:0} to{bottom:0;opacity:1}
            }
            #part{
              animation:fading 10s
            }
             .form-control{
              height:34px;
              width: 100px;
             }
            #butt{
              background-color:#000000;
              border : #000000;
            }
            h2{
              font-family: fantasy;
              font-size: 50px;
              display: inline;
            }
            h1{
              font-family: fantasy;
              font-size:70px;
              color: red;
              display: inline;
            }
            h3{
              font-size:20px;
              font-family: fantasy;
            }
            #pricetag{
              height:auto;
              width:700px;
              top:80%;
              right:5%;
              position: fixed;
              z-index: 10;
              background:#FFFFFF;
            }
            #gallery{ 
              position:relative; 
              z-index: 1; 
              animation:animatebottom 2s 
            } 
            @keyframes fading{
              0%{opacity:0}50%{opacity:1}100%}
            #order{
              position:fixed;
              top:73%;
              right:29%;
              z-index:10;
              font-size:35px;
            }
           </style>
      </head>  


      <body>  
           <br /> 
           <div class="container" style="width:auto;">  
                <div class="text"><h2 align="center"> Take Home some</h2> <h1 align="center"> GOODVIBES</h1></div>
                <h3 align="center" id="part">Be part of our movement.</h3> <br> 
                <div class="table-responsive">  
                    <h2 id="order">Order Details</h2>
                     <table class="table table-bordered" id="pricetag">  
                          <tr>  
                               <th width="40%">Item Name</th>  
                               <th width="10%">Quantity</th>  
                               <th width="20%">Price</th>  
                               <th width="15%">Total</th>  
                               <th width="5%">Action</th>  
                          </tr>  
                          <?php   
                          if(!empty($_SESSION["shopping_cart"]))  
                          {  
                               $total = 0;  
                               foreach($_SESSION["shopping_cart"] as $keys => $values)  
                               {  
                          ?>  
                          <tr>  
                               <td><?php echo $values["item_name"]; ?></td>  
                               <td><?php echo $values["item_quantity"]; ?></td>  
                               <td>$ <?php echo $values["item_price"]; ?></td>  
                               <td>$ <?php echo number_format($values["item_quantity"] * $values["item_price"], 2); ?></td>  
                               <td><a href="index2.php?action=delete&id=<?php echo $values["item_id"]; ?>"><span class="text-danger">Remove</span></a></td>  

                          </tr>  
                          <?php  
                                    $total = $total + ($values["item_quantity"] * $values["item_price"]);  
                               }  
                          ?>  
                          <tr>  
                               <td colspan="3" align="right">Total</td>  
                               <td align="right">$ <?php echo number_format($total, 2); ?></td>  
                               <td bgcolor="#A00000" onclick="confirm()"><span style="font-weight:bold; color:#ffffff;">CHECKOUT</span></td>  
                               <script>function confirm(){alert("Proceed to CHECKOUT! #GOODVIBEZ");} </script>
                          </tr>  
                          <?php  
                          }  
                          ?>  
                     </table>  
                </div>  
                <script>function onClick(element) {
                 document.getElementById("img01").src = element.src;
                 document.getElementById("modal01").style.display = "block";
                 var captionText = document.getElementById("caption");
                 captionText.innerHTML = element.alt;
                  }</script>

            <div id="modal01" class="w3-modal w3-black" onclick="this.style.display='none'">
            <span class="w3-button w3-xxlarge w3-black w3-padding-large w3-display-topright" title="Close Modal Image">X</span>
            <div class="w3-modal-content w3-animate-zoom w3-center w3-transparent w3-padding-64">
            <img id="img01" class="w3-image">
            <p id="caption" class="w3-opacity w3-large"></p>
            </div>
            </div>


                <?php  
                $query = "SELECT * FROM tbl_product ORDER BY id ASC";  
                $result = mysqli_query($connect, $query);  
                if(mysqli_num_rows($result) > 0)  
                {  
                     while($row = mysqli_fetch_array($result))  
                     {  
                ?>  
                <div class="col-md-4" id="gallery">  
                     <form method="post" action="index2.php?action=add&id=<?php echo $row["id"]; ?>">  
                          <div style="border:#ffffff; background-color:#ffffff; border-radius:5px; padding:16px;" align="center">  
                               <img src="<?php echo $row["image"]; ?>" class="img" id="img01" onclick="onClick(this)"/><br />  
                               <h4 class="text-info"><?php echo $row["name"]; ?></h4>  
                               <h4 class="text-danger">$ <?php echo $row["price"]; ?></h4>  
                               <input type="text" name="quantity" class="form-control" value="1" />  
                               <input type="hidden" name="hidden_name" value="<?php echo $row["name"]; ?>" />  
                               <input type="hidden" name="hidden_price" value="<?php echo $row["price"]; ?>" />  
                               <input type="submit" name="add_to_cart" style="margin-top:5px;" class="btn btn-success" id="butt" value="Add to Cart" />  
                          </div>
                          <br><br><br>
                     </form>  
                </div>
                <?php  
                     }  
                }  
                ?>  
                <div style="clear:both"></div>  
                <br />  

                
           </div>  
           <br />  
      </body>  
 </html>
   