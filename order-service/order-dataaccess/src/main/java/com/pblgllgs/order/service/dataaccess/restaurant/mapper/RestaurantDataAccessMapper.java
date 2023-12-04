package com.pblgllgs.order.service.dataaccess.restaurant.mapper;
/*
 *
 * @author pblgl
 * Created on 30-11-2023
 *
 */

import com.pblgllgs.order.service.dataaccess.restaurant.entity.RestaurantEntity;
import com.pblgllgs.order.service.dataaccess.restaurant.exception.RestaurantDataAccessException;
import com.pblgllgs.order.service.domain.entity.Product;
import com.pblgllgs.order.service.domain.entity.Restaurant;
import com.pblgllgs.valueobject.Money;
import com.pblgllgs.valueobject.ProductId;
import com.pblgllgs.valueobject.RestaurantId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestaurantDataAccessMapper {

    public List<UUID> restaurantToRestaurantProducts(Restaurant restaurant) {
        return restaurant.getProducts().stream()
                .map(
                        product -> product.getId().getValue()
                ).collect(Collectors.toList());
    }

    public Restaurant restaurantEntityToRestaurant(List<RestaurantEntity> restaurantEntities) {
        RestaurantEntity restaurantEntity = restaurantEntities.stream()
                .findFirst().orElseThrow(() -> new RestaurantDataAccessException("Restaurant could not be found!"));
        List<Product> restaurantProducts = restaurantEntities.stream().map(
                entity -> new Product(
                        new ProductId(entity.getProductId()),
                        entity.getProductName(),
                        new Money(entity.getProductPrice()))
        ).collect(Collectors.toList());
        return Restaurant.builder()
                .products(restaurantProducts)
                .restaurantId(new RestaurantId(restaurantEntity.getRestaurantId()))
                .active(restaurantEntity.getRestaurantActive())
                .build();
    }
}
