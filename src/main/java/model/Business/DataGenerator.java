public class DataGenerator {
    public void generateTestData(Business business) {
        // Generate 5 suppliers
        for(int i = 0; i < 5; i++) {
            Supplier supplier = new Supplier("Supplier" + i);
            
            // Generate 10 products per supplier
            for(int j = 0; j < 10; j++) {
                Product product = new Product("Product" + j);
                supplier.addProduct(product);
            }
            
            business.getSupplierDirectory().addSupplier(supplier);
        }
        
        // Generate 10 customers
        for(int i = 0; i < 10; i++) {
            Customer customer = new Customer("Customer" + i);
            business.getCustomerDirectory().addCustomer(customer);
        }
        
        // Generate 10 orders per product
        generateOrders(business);
    }
    
    private void generateOrders(Business business) {
        Random random = new Random();
        for(Product product : business.getAllProducts()) {
            for(int i = 0; i < 10; i++) {
                Customer randomCustomer = business.getRandomCustomer();
                Order order = new Order(randomCustomer);
                order.addOrderItem(new OrderItem(product, 
                    product.getTargetPrice() * (0.8 + random.nextDouble() * 0.4)));
                business.getMasterOrderList().addOrder(order);
            }
        }
    }
} 