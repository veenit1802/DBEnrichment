package org.example.mapper;

public class ObjectMapper {
    private Integer id;
    private String imageUrl;

    private String productId;

    private String productName;

    private float productPrice;

    private String productType;

    public Integer getId() {
        return id;
    }

    public ObjectMapper(Integer id, String imageUrl, String productId, String productName, float productPrice, String productType) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productType = productType;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
