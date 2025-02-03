package com.blumenstreetdoo.nora_pub.data

import com.blumenstreetdoo.nora_pub.data.db.entity.FavoriteBeerEntity
import com.blumenstreetdoo.nora_pub.domain.models.Beer
import com.blumenstreetdoo.nora_pub.domain.models.FavoriteBeer

fun FavoriteBeerEntity.toDomain(): FavoriteBeer {
    return FavoriteBeer(
        id = id,
        name = name,
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

fun Beer.toFavoriteBeer(): FavoriteBeer {
    return FavoriteBeer(
        id = this.id,
        name = this.name,
        description = this.description,
        abv = this.abv,
        imageUrl = this.imageUrl,
        brewery = this.brewery,
        beerIbu = this.beerIbu,
        beerStyleId = this.beerStyleId,
        beerStyle = this.beerStyle,
        note = null
    )
}
