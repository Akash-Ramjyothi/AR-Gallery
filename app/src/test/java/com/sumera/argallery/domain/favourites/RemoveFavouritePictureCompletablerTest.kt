package com.sumera.argallery.domain.favourites

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.sumera.argallery.data.mapper.PictureMapper
import com.sumera.argallery.data.store.persistence.AppDatabase
import com.sumera.argallery.data.store.persistence.FavouritePicturesDao
import com.sumera.argallery.data.store.persistence.model.PersistedPicture
import com.sumera.argallery.data.store.ui.model.Picture
import io.github.plastix.rxschedulerrule.RxSchedulerRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class RemoveFavouritePictureCompletablerTest {

    @Rule
    @JvmField var rxRule: TestRule = RxSchedulerRule()

    var mockAppDatabase = mock<AppDatabase>()
    var mockMapper = mock<PictureMapper>()
    var mockFavouritePicturesDato = mock<FavouritePicturesDao>()
    var mockPicture = mock<Picture>()
    var mockPersistencePicture = mock<PersistedPicture>()

    lateinit var removeFavouritePictureCompletabler: RemoveFavouritePictureCompletabler

    @Before
    fun setUp() {
        removeFavouritePictureCompletabler = RemoveFavouritePictureCompletabler(mockAppDatabase, mockMapper)
        whenever(mockAppDatabase.favouritePicturesDao()).thenReturn(mockFavouritePicturesDato)
        whenever(mockMapper.toPersistedPicture(mockPicture)).thenReturn(mockPersistencePicture)
    }

    @Test
    fun `execute should remove picture from dao`() {
        // WHEN
        val result = removeFavouritePictureCompletabler.init(mockPicture).execute().test()

        // THEN
        result.assertComplete()
        verify(mockFavouritePicturesDato).deletePicture(mockPersistencePicture)
    }
}