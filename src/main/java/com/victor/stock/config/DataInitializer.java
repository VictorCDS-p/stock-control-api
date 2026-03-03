package com.victor.stock.config;

import com.victor.stock.entity.Product;
import com.victor.stock.entity.ProductMaterial;
import com.victor.stock.entity.RawMaterial;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class DataInitializer {

    @Transactional
    void onStart(@Observes StartupEvent ev) {

        if (Product.count() > 0) {
            return;
        }

        List<RawMaterial> materials = new ArrayList<>();

        materials.add(createMaterial("RM001", "Carbon Steel", 5000));
        materials.add(createMaterial("RM002", "Stainless Steel", 3000));
        materials.add(createMaterial("RM003", "Aluminum Sheet", 4000));
        materials.add(createMaterial("RM004", "Copper Wire", 2500));
        materials.add(createMaterial("RM005", "Industrial Plastic", 6000));
        materials.add(createMaterial("RM006", "Rubber Compound", 3500));
        materials.add(createMaterial("RM007", "Glass Panel", 1200));
        materials.add(createMaterial("RM008", "Silicon Chip", 2000));
        materials.add(createMaterial("RM009", "Lithium Battery Cell", 1800));
        materials.add(createMaterial("RM010", "Electric Motor Core", 900));
        materials.add(createMaterial("RM011", "Hydraulic Pump Unit", 700));
        materials.add(createMaterial("RM012", "Gear Assembly", 1500));
        materials.add(createMaterial("RM013", "Ball Bearings", 4000));
        materials.add(createMaterial("RM014", "Industrial Adhesive", 2200));
        materials.add(createMaterial("RM015", "Steel Rod", 2800));
        materials.add(createMaterial("RM016", "PVC Pipe", 3200));
        materials.add(createMaterial("RM017", "Circuit Board", 2600));
        materials.add(createMaterial("RM018", "Cooling Fan Module", 1400));
        materials.add(createMaterial("RM019", "LED Module", 5000));
        materials.add(createMaterial("RM020", "Packaging Material", 10000));

        List<Product> products = new ArrayList<>();

        products.add(createProduct("P001", "Industrial Drill", 850.00));
        products.add(createProduct("P002", "Hydraulic Press", 5200.00));
        products.add(createProduct("P003", "Electric Generator", 7800.00));
        products.add(createProduct("P004", "Air Compressor", 3100.00));
        products.add(createProduct("P005", "CNC Machine", 15000.00));
        products.add(createProduct("P006", "Welding Machine", 1200.00));
        products.add(createProduct("P007", "Industrial Conveyor", 6400.00));
        products.add(createProduct("P008", "Water Pump", 980.00));
        products.add(createProduct("P009", "Forklift Motor", 4300.00));
        products.add(createProduct("P010", "Solar Inverter", 2600.00));
        products.add(createProduct("P011", "Power Transformer", 8900.00));
        products.add(createProduct("P012", "Industrial Fan", 750.00));
        products.add(createProduct("P013", "LED Lighting Panel", 420.00));
        products.add(createProduct("P014", "Control Panel System", 5100.00));
        products.add(createProduct("P015", "Packaging Machine", 13200.00));
        products.add(createProduct("P016", "Injection Molding Machine", 22000.00));
        products.add(createProduct("P017", "Cooling System Unit", 4700.00));
        products.add(createProduct("P018", "Battery Storage Module", 6900.00));
        products.add(createProduct("P019", "Electric Engine Assembly", 9800.00));
        products.add(createProduct("P020", "Industrial Robot Arm", 28000.00));

        Random random = new Random();

        for (Product product : products) {

            int materialCount = 2 + random.nextInt(3); // 2 a 4 materiais

            for (int i = 0; i < materialCount; i++) {

                RawMaterial material =
                        materials.get(random.nextInt(materials.size()));

                ProductMaterial pm = new ProductMaterial();
                pm.product = product;
                pm.rawMaterial = material;
                pm.requiredQuantity = 5 + random.nextInt(20);
                pm.persist();
            }
        }

        System.out.println("🔥 Database seeded with realistic industrial data!");
    }

    private RawMaterial createMaterial(String code, String name, int stock) {
        RawMaterial m = new RawMaterial();
        m.code = code;
        m.name = name;
        m.stockQuantity = stock;
        m.persist();
        return m;
    }

    private Product createProduct(String code, String name, double price) {
        Product p = new Product();
        p.code = code;
        p.name = name;
        p.price = BigDecimal.valueOf(price);
        p.persist();
        return p;
    }
}