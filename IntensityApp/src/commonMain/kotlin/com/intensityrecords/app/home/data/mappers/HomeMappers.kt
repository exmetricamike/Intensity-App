package com.intensityrecords.app.home.data.mappers

import com.intensityrecords.app.home.data.dto.DailyVideoDto
import com.intensityrecords.app.home.data.dto.HomeDto
import com.intensityrecords.app.home.data.dto.HotelThemeDto
import com.intensityrecords.app.home.data.dto.UiBlockDto
import com.intensityrecords.app.home.domain.DailyVideo
import com.intensityrecords.app.home.domain.HotelTheme
import com.intensityrecords.app.home.domain.UiBlock
import com.intensityrecords.app.home.domain.UiConfig

fun HomeDto.toUiConfig(): UiConfig {
    return UiConfig(
        id = id,
        name = name,
        urlName = urlName,
        design = uiBlockDesign,
        blocks = uiBlocks
            .sortedBy { it.order }
            .map { it.toUiBlock() },
        theme = theme?.toHotelTheme()
    )
}

fun UiBlockDto.toUiBlock(): UiBlock {
    return UiBlock(
        id = id,
        titleEn = titleEn,
        titleFr = titleFr,
        titleNl = titleNl,
        imageUrl = image,
        order = order,
        url = url
    )
}

fun DailyVideoDto.toDailyVideo(): DailyVideo {
    return DailyVideo(
        id = id,
        date = date,
        titleEn = titleEn,
        titleFr = titleFr,
        titleNl = titleNl,
        taglineEn = taglineEn,
        taglineFr = taglineFr,
        taglineNl = taglineNl,
        coverImage = coverImage,
        muxAssetIdEn = muxAssetIdEn,
        muxAssetIdFr = muxAssetIdFr,
        muxAssetIdNl = muxAssetIdNl,
        durationLabelMin = durationLabelMin,
        caloriesBurnedLabel = caloriesBurnedLabel,
        focusLabelEn = focusLabelEn,
        focusLabelFr = focusLabelFr,
        focusLabelNl = focusLabelNl,
        tags = tags
    )
}

fun HotelThemeDto.toHotelTheme(): HotelTheme {
    return HotelTheme(
        id = id,
        hotelName = hotelName,
        hotelUrlName = hotelUrlName,
        hotelLogo = hotelLogo,
        hotelTagline = hotelTagline,
        showLogo = showLogo,
        showHeader = showHeader,
        showTagline = showTagline,
        primaryColor = primaryColor,
        secondaryColor = secondaryColor,
        headerTextColor = headerTextColor,
        titleTextColor = titleTextColor,
        textColor = textColor
    )
}
