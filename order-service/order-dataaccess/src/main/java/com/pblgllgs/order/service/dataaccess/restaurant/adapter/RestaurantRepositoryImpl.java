package com.pblgllgs.order.service.dataaccess.restaurant.adapter;
/*
 *
 * @author pblgl
 * Created on 30-11-2023
 *
 */

import com.pblgllgs.order.service.dataaccess.restaurant.entity.RestaurantEntity;
import com.pblgllgs.order.service.dataaccess.restaurant.mapper.RestaurantDataAccessMapper;
import com.pblgllgs.order.service.dataaccess.restaurant.repository.RestaurantJpaRepository;
import com.pblgllgs.order.service.domain.entity.Restaurant;
import com.pblgllgs.ports.output.repository.RestaurantRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private final RestaurantJpaRepository restaurantJpaRepository;
    private final RestaurantDataAccessMapper restaurantDataAccessMapper;

    public RestaurantRepositoryImpl(RestaurantJpaRepository restaurantJpaRepository, RestaurantDataAccessMapper restaurantDataAccessMapper) {
        this.restaurantJpaRepository = restaurantJpaRepository;
        this.restaurantDataAccessMapper = restaurantDataAccessMapper;
    }

    @Override
    public Optional<Restaurant> findRestaurantInformation(Restaurant restaurant) {
        List<UUID> restaurantProducts = restaurantDataAccessMapper.restaurantToRestaurantProducts(restaurant);
        Optional<List<RestaurantEntity>> restaurantEntities =
                restaurantJpaRepository.findByRestaurantIdAndProductIdIn(
                        restaurant.getId().getValue(),
                        restaurantProducts
                );
        return restaurantEntities.map(restaurantDataAccessMapper::restaurantEntityToRestaurant);
    }
}
