package com.ferechamitebeyli.data.mapper.base

/**
 * Created by Ferec Hamitbeyli on 5.04.2024.
 */

interface BaseMapper<M, U> {
    fun mapToUiModel(model: M): U

    fun mapToUiModelList(models: List<M>): List<U> {
        return models.mapTo(mutableListOf(), ::mapToUiModel)
    }
}