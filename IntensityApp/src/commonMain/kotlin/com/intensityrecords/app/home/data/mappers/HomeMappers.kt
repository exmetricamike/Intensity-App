package com.intensityrecords.app.home.data.mappers

import com.intensityrecords.app.home.data.dto.HomeDto
import com.intensityrecords.app.home.data.dto.HotelThemeDto
import com.intensityrecords.app.home.data.dto.UiBlockDto
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
        title = title,
        imageUrl = image,
        order = order,
        url = url
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
