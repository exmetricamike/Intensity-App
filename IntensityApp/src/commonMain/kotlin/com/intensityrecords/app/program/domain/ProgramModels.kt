package com.intensityrecords.app.program.domain

data class ProgramSection(
    val id: Int,
    val title: String,
    val coverImage: String?,
    val collections: List<ProgramCollection>
)

data class ProgramCollection(
    val id: Int,
    val name: String,
    val description: String,
    val coverImage: String?,
    val collectionType: String
)

data class ProgramCollectionDetail(
    val id: Int,
    val name: String,
    val description: String,
    val coverImage: String?,
    val videos: List<ProgramVideo>
)

data class ProgramVideo(
    val id: Int,
    val title: String,
    val description: String,
    val coverImage: String?,
    val muxAssetId: String?
)
