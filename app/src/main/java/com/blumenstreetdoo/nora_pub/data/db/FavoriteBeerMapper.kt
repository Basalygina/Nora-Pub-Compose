package com.blumenstreetdoo.nora_pub.data.db

import com.blumenstreetdoo.nora_pub.data.db.entity.FavoriteBeerEntity
import com.blumenstreetdoo.nora_pub.domain.models.FavoriteBeer

fun FavoriteBeerEntity.toDomain(): FavoriteBeer {
    return FavoriteBeer(
        id = id,
        name = name,
        type = type,
        description = description,
        abv = abv,
        imageUrl = imageUrl,
        brewery = brewery,
        beerIbu = beerIbu,
        beerStyleId = beerStyleId,
        beerStyle = beerStyle,
        note = note
    )
}

fun FavoriteBeer.toEntity(): FavoriteBeerEntity {
    return FavoriteBeerEntity(
        id = id,
        name = name,
        type = type,
        description = description,
        abv = abv,
        imageUrl = imageUrl,
        brewery = brewery,
        beerIbu = beerIbu,
        beerStyleId = beerStyleId,
        beerStyle = beerStyle,
        note = note
    )
}
