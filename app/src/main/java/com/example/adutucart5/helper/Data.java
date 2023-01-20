package com.example.adutucart5.helper;

import com.example.adutucart5.model.Offer;
import com.example.adutucart5.model.Product;
import com.example.adutucart5.model.Category;
import com.example.adutucart5.model.Stores;

import java.util.ArrayList;
import java.util.List;


public class Data {
    List<Category> categoryList = new ArrayList<>();
    List<Product> productList = new ArrayList<>();
    List<Product> newList = new ArrayList<>();
    List<Product> popularList = new ArrayList<>();
    List<Offer> offerList = new ArrayList<>();
    List<Stores> storesList = new ArrayList<>();

    public List<Category> getCategoryList() {
        Category category = new Category("1", "Food", "https://image.flaticon.com/icons/png/512/262/262344.png");
        categoryList.add(category);
        category = new Category("2", "Home & Cleaning", "https://lisasnatural.com/wp-content/uploads/2018/05/housecleanicon-300x228.jpg");
        categoryList.add(category);
        category = new Category("3", "Baby Care", "https://tips4tots.files.wordpress.com/2015/08/medical-insurance-free-icon.png");
        categoryList.add(category);
        category = new Category("4", "sports & Nutrition", "https://kathleenhalme.com/images/nutrition-clipart-sport.jpg");
        categoryList.add(category);
        category = new Category("5", "Pet care", "http://kasperstromman.files.wordpress.com/2013/05/dog-cat-above-board.jpg");
        categoryList.add(category);
        category = new Category("6", "Health & Household", "https://thumbs.dreamstime.com/b/household-cleaning-products-accessories-basket-there-mop-detergents-rubber-gloves-glass-cleaner-sponges-89944820.jpg");
        categoryList.add(category);
        return categoryList;
    }

//    public void addProductList(Product product){
//        productList.add(product);
//    }
//
//    public List<Product> getAllProductList(){
//        return productList;
//    }

    public List<Product> getProductList() {
        Product product = new Product("1", "1", "Apple", "", "1 Kg", "Php.", "20", "10% OFF", "https://storage.googleapis.com/zopnow-static/images/products/320/fresh-apple-red-delicious-v-500-g.png");
        productList.add(product);
        product = new Product("2", "1", "Banana", "", "1 Bounch", "Php.", "10", "20% OFF", "https://images-na.ssl-images-amazon.com/images/I/21DejQuoT2L.jpg");
        productList.add(product);
        product = new Product("3", "2", "House Clean Liquid", "", "1 Lit.", "Php.", "25", "", "http://sunsetcleaningcia.com/wp-content/uploads/2016/05/houseclean.png");
        productList.add(product);
        product = new Product("4", "2", "House Clean Brush", "", "1 Piece", "Php.", "10", "", "https://www.clean-hoouse.com/wp-content/uploads/2017/09/13.png");
        productList.add(product);
        product = new Product("5", "3", "Pampers", "", "1 Piece", "Php.", "20", "10% OFF", "https://cdn.bmstores.co.uk/images/hpcProductImage/imgFull/311448-Pampers-Baby-Dry-Size-4-Maxi-251.jpg");
        productList.add(product);
        product = new Product("6", "3", "Baby Oil", "", "500 Ml", "Php.", "31", "", "https://www.fortunaonline.net/media/catalog/product/cache/1/small_image/295x/040ec09b1e35df139433887a97daa66f/n/k/nkbcp12_-_xia-shib-ao-baby-oil-200ml.png");
        productList.add(product);
        product = new Product("7", "4", "Apple", "", "1 Kg", "Php.", "20", "", "https://storage.googleapis.com/zopnow-static/images/products/320/fresh-apple-red-delicious-v-500-g.png");
        productList.add(product);
        product = new Product("8", "4", "sardines", "", "1 Kg", "Php.", "20", "", "https://i.imgur.com/wG1rbTP.jpg");
        productList.add(product);
        product = new Product("9", "5", "Springles", "", "1 Kg", "Php.", "20", "", "https://i.imgur.com/xs19KGI.jpg");
        productList.add(product);
        product = new Product("10", "5", "Strawberries", "", "1 Kg", "Php.", "20", "", "https://i.imgur.com/7gSwHkl.jpg");
        productList.add(product);
        product = new Product("11", "6", "Tilapia", "", "1 Kg", "Php.", "20", "", "https://i.imgur.com/gREvrBc.jpg");
        productList.add(product);
        product = new Product("12", "6", "TissueRoll", "", "1 Kg", "Php.", "20", "", "https://i.imgur.com/cmoGSio.jpg");
        productList.add(product);
        product = new Product("13", "1", "Litche", "", "1 Kg", "Php.", "20", "30%OFF", "https://cdn.shopify.com/s/files/1/0665/4989/products/lichee-485x400_grande.jpg");
        productList.add(product);
        return productList;
    }

    public List<Product> getNewList() {
        Product product = new Product("1", "1", "Apple", "", "1 Kg", "Php.", "20", "10% OFF", "https://storage.googleapis.com/zopnow-static/images/products/320/fresh-apple-red-delicious-v-500-g.png");
        newList.add(product);
        product = new Product("2", "1", "Banana", "", "1 Bounch", "Php.", "10", "20% OFF", "https://images-na.ssl-images-amazon.com/images/I/21DejQuoT2L.jpg");
        newList.add(product);
        product = new Product("3", "2", "House Clean Liquid", "", "1 Lit.", "Php.", "25", "", "http://sunsetcleaningcia.com/wp-content/uploads/2016/05/houseclean.png");
        newList.add(product);
        product = new Product("4", "2", "House Clean Brush", "", "1 Piece", "Php.", "10", "", "https://www.clean-hoouse.com/wp-content/uploads/2017/09/13.png");
        newList.add(product);
        product = new Product("5", "3", "Pampers", "", "1 Piece", "20", "Php.", "10% OFF", "https://cdn.bmstores.co.uk/images/hpcProductImage/imgFull/311448-Pampers-Baby-Dry-Size-4-Maxi-251.jpg");
        newList.add(product);
        return newList;
    }

    public List<Product> getPopularList() {
        Product product = new Product("6", "3", "Baby Oil", "", "500 Ml", "Php.", "31", "", "https://www.fortunaonline.net/media/catalog/product/cache/1/small_image/295x/040ec09b1e35df139433887a97daa66f/n/k/nkbcp12_-_xia-shib-ao-baby-oil-200ml.png");
        popularList.add(product);
        product = new Product("7", "4", "Apple", "", "1 Kg", "Php.", "20", "", "https://storage.googleapis.com/zopnow-static/images/products/320/fresh-apple-red-delicious-v-500-g.png");
        popularList.add(product);
        product = new Product("8", "4", "Bokchoy", "", "1 Kg", "Php.", "20", "", "https://i.imgur.com/D9g6nvo.jpg");
        popularList.add(product);
        product = new Product("9", "5", "Cabbage", "", "1 Kg", "Php.", "20", "", "https://purepng.com/public/uploads/large/purepng.com-cabbagecabbageplantvegetablesgreen-17015272418151g4zr.png");
        popularList.add(product);
        product = new Product("10", "5", "Dove", "", "1 Kg", "Php.", "20", "", "https://i.imgur.com/xFHaHwg.jpg");
        popularList.add(product);
        product = new Product("11", "6", "Grapes", "", "1 Kg", "Php.", "20", "", "https://i.imgur.com/AmLQ4yb.jpg");
        popularList.add(product);
        product = new Product("12", "6", "Head_shoulder", "", "1 Kg", "Php.", "20", "", "https://i.imgur.com/a4U67Yy.jpg");
        popularList.add(product);
        product = new Product("13", "1", "Milkfish", "", "1 Kg", "Php.", "20", "30%OFF", "https://i.imgur.com/066QMtL.jpg");
        popularList.add(product);
        return popularList;
    }

    public List<Offer> getOfferList() {
        Offer offer = new Offer("http://1.bp.blogspot.com/-MMt-60IWEdw/VqZsh45Dv8I/AAAAAAAAMHg/70D9tVowlyc/s1600/askmegrocery-republic-day-offer.jpg");
        offerList.add(offer);
        offer = new Offer("http://www.lootkaro.com/wp-content/uploads/2016/05/as1.jpg");
        offerList.add(offer);
        offer = new Offer("https://453xbcknr3t3ahr522mms518-wpengine.netdna-ssl.com/wp-content/themes/iga-west/images/banner-save-more.jpg");
        offerList.add(offer);
        offer = new Offer("https://images-eu.ssl-images-amazon.com/images/G/31/img16/Grocery/SVD/July18/750x375.png");
        offerList.add(offer);
        offer = new Offer("https://images-eu.ssl-images-amazon.com/images/G/31/img16/Grocery/BreakfastStore/kmande_2018-06-15T12-00_f010a5_1121973_grocery_750x375.jpg");
        offerList.add(offer);
        offer = new Offer("http://www.enjoygrocery.com/images/enjoygrocery-offer.jpg");
        offerList.add(offer);

        return offerList;
    }

    public List<Stores> getStoreList(){

        Stores stores = new Stores("https://i.imgur.com/8HG1ryC.jpg");
        storesList.add(stores);
        stores  = new Stores("https://i.imgur.com/8HG1ryC.jpg");
        storesList.add(stores);
        stores  = new Stores("https://i.imgur.com/8HG1ryC.jpg");
        storesList.add(stores);

        return storesList;
    }

}

