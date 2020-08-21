package create.builder;

/**
 * @author zhangchun
 */
public class Product {

    private String TV;
    private String sofa;

    public static class ProductBuilder{
        private String TV;
        private String sofa;
        ProductBuilder() {
        }

        public ProductBuilder buildTV(String TV) {
            this.TV = TV;
            return this;
        }

        public ProductBuilder buildSofa(String sofa) {
            this.sofa = sofa;
            return this;
        }

        public Product build() {
            Product product = new Product();
            product.TV = TV;
            product.sofa = sofa;
            return product;
        }

    }

    public static ProductBuilder  builder() {
        return new ProductBuilder();
    }

    @Override
    public String toString() {
        return "Product{" +
                "TV='" + TV + '\'' +
                ", sofa='" + sofa + '\'' +
                '}';
    }
}
