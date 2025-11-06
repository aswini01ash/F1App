package com.example.racerapplication.ui.home

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.racerapplication.R
import com.example.racerapplication.data.model.Driver
import com.example.racerapplication.data.model.Race
import com.example.racerapplication.ui.components.HomeTopSlider
import com.example.racerapplication.ui.components.LoadingIndicator
import com.example.racerapplication.util.DateUtils

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    onNavigateToDetail: (Race) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            LoadingIndicator()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF1A1A1A))
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Top Driver Card
                uiState.driver?.let { driver ->
                    HomeTopSlider(driver = uiState.driver)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Two Cards Row (Race Info + Education)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Left side: Race Info Card
                    uiState.race?.let { race ->
                        RaceInfoCard(
                            race = race,
                            onClick = { onNavigateToDetail(race) },
                            modifier = Modifier
                                .weight(1f)
                                .height(120.dp)
                        )
                    }

                    // Right side: Column with Distance and Education
                    Column(
                        modifier = Modifier
                            .clickable {
                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    "https://www.instagram.com/boxbox_club/".toUri()
                                )
                                context.startActivity(intent)
                            }
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        uiState.race?.let { race ->
                            RaceDistanceCard(
                                race = race,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(55.dp)
                            )
                        }

                        // Formula 1 Education Card
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF2563EB))
                                .clickable {
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        "https://blog.boxbox.club/tagged/beginners-guide".toUri()
                                    )
                                    context.startActivity(intent)
                                }
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_education_icon),
                                    contentDescription = "Education",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Column {
                                    Text(
                                        "Formula 1",
                                        color = Color.White,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        "Education",
                                        color = Color.White,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }


                Spacer(modifier = Modifier.height(16.dp))

                // F1 25 Game Card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_instagram),
                        contentDescription = "F1 25",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Overlay gradient
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.7f)
                                    )
                                )
                            )
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RaceDistanceCard(
    race: Race,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(55.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        // Background split (red/black)
        Row(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight()
                    .background(Color(0xFFE10600))
            )
            Box(
                modifier = Modifier
                    .weight(0.6f)
                    .fillMaxHeight()
                    .background(Color.Black)
            )
        }

        // Foreground content
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(id = R.drawable.km_icon),
                contentDescription = "Distance Icon",
                tint = Color.White,
                modifier = Modifier.size(22.dp)
            )

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "7015.3km",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun DriverCard(driver: Driver) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFF8A3D),
                        Color(0xFFFF6B00)
                    )
                )
            )
    ) {
        // Driver image background
        Image(
            painter = painterResource(id = R.drawable.driver_placeholder),
            contentDescription = "Driver",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .aspectRatio(0.7f),
            contentScale = ContentScale.Fit,
            alpha = 0.9f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            // Position badge
            Box(
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.9f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "P",
                        color = Color(0xFFFF6B00),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        driver.position.toString(),
                        color = Color(0xFFFF6B00),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "vs",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "P${driver.position + 1}",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Driver name - large
            Text(
                driver.firstName,
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 36.sp,
                fontWeight = FontWeight.Normal
            )

            // Points
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(
                    driver.points.toString(),
                    color = Color.White,
                    fontSize = 56.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 56.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "PTS",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RaceInfoCard(
    race: Race,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val nextSession = DateUtils.getNextSession(race.sessions)

    Box(
        modifier = modifier
            .height(120.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF047857))
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Column {
            // Session name and circuit icon
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_circuit), // Add circuit icon
                    contentDescription = "Circuit",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    nextSession?.sessionName ?: "Race",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Race location/distance
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_location), // Add location icon
                    contentDescription = "Location",
                    tint = Color.White,
                    modifier = Modifier.size(12.dp)
                )
                Text(
                    "7015.3km", // You can calculate actual distance
                    color = Color.White,
                    fontSize = 11.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Time
            nextSession?.let { session ->
                Text(
                    DateUtils.formatToLocalTime(session.startTime),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}