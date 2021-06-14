package com.sumera.argallery.domain.favourites

import com.sumera.argallery.data.mapper.PictureMapper
import com.sumera.argallery.data.store.persistence.AppDatabase
import com.sumera.argallery.data.store.ui.model.Picture
import com.sumera.argallery.domain.base.BaseCompletabler
import io.reactivex.Completable
import javax.inject.Inject

class AddFavouritePictureCompletabler @Inject constructor(
        private val appDatabase: AppDatabase,
        private val pictureMapper: PictureMapper
) : BaseCompletabler() {

    private lateinit var picture: Picture

    fun init(picture: Picture): AddFavouritePictureCompletabler {
        this.picture = picture
        return this
    }

    override fun create(): Completable {
        return Completable.fromCallable {
            appDatabase.favouritePicturesDao()
                    .insertPicture(pictureMapper.toPersistedPicture(picture))
        }
    }
}