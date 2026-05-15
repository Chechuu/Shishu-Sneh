package com.shishusneh.core.ui

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.shishusneh.core.data.GrowthRecord
import com.shishusneh.core.data.Milestone
import java.text.SimpleDateFormat
import java.util.*

// Premium Color Palette
val SoftPink = Color(0xFFFCE4EC)
val SoftBlue = Color(0xFFE1F5FE)
val SoftMint = Color(0xFFE8F5E9)
val PrimaryPink = Color(0xFFE91E63)
val PrimaryBlue = Color(0xFF03A9F4)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: BabyViewModel) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Home", "Growth", "Vaccines", "Care")
    
    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
                val icons = listOf(Icons.Default.Home, Icons.Default.ShowChart, Icons.Default.Vaccines, Icons.Default.ChildCare)
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = { Icon(icons[index], contentDescription = title) },
                        label = { Text(title, style = MaterialTheme.typography.labelSmall) },
                        colors = NavigationBarItemDefaults.colors(selectedIconColor = PrimaryPink, selectedTextColor = PrimaryPink, indicatorColor = SoftPink)
                    )
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding).background(
            Brush.verticalGradient(listOf(SoftPink, Color.White))
        )) {
            when (selectedTab) {
                0 -> DashboardScreen(viewModel)
                1 -> GrowthScreen(viewModel)
                2 -> VaccinationScreen(viewModel)
                3 -> CareHubScreen(viewModel)
            }
        }
    }
}

@Composable
fun DashboardScreen(viewModel: BabyViewModel) {
    val records by viewModel.growthRecords.collectAsState(initial = emptyList())
    val lastWeight = records.lastOrNull()?.weight ?: 0f

    LazyColumn(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        item {
            Text("Hello, Mom! 👋", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Black)
            Text("Here's your baby's status today", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatusCard("Weight", "$lastWeight kg", Icons.Default.Scale, PrimaryPink, Modifier.weight(1f))
                StatusCard("Next Vaccine", "6 Weeks", Icons.Default.Event, PrimaryBlue, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Text("Daily Health Tip", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                color = Color.White,
                shadowElevation = 4.dp
            ) {
                Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Lightbulb, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(40.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Tummy time helps build your baby's neck and shoulder muscles. Try 5 mins today!", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
fun StatusCard(title: String, value: String, icon: ImageVector, color: Color, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        color = color.copy(alpha = 0.1f),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.2f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = color)
            Spacer(modifier = Modifier.height(12.dp))
            Text(title, style = MaterialTheme.typography.labelSmall, color = color, fontWeight = FontWeight.Bold)
            Text(value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black)
        }
    }
}

@Composable
fun GrowthScreen(viewModel: BabyViewModel) {
    val records by viewModel.growthRecords.collectAsState(initial = emptyList())
    var weight by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Text("Growth Tracker", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Black)
        Spacer(modifier = Modifier.height(20.dp))

        Surface(
            modifier = Modifier.fillMaxWidth().height(300.dp),
            shape = RoundedCornerShape(32.dp),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            AndroidView(
                factory = { context ->
                    LineChart(context).apply {
                        description.isEnabled = false
                        setTouchEnabled(true)
                        xAxis.position = XAxis.XAxisPosition.BOTTOM
                        xAxis.setDrawGridLines(false)
                        axisRight.isEnabled = false
                        axisLeft.setDrawGridLines(true)
                        axisLeft.gridColor = android.graphics.Color.LTGRAY
                        legend.isEnabled = false
                    }
                },
                modifier = Modifier.fillMaxSize().padding(16.dp),
                update = { chart ->
                    val entries = records.mapIndexed { index, record -> Entry(index.toFloat(), record.weight) }
                    val dataSet = LineDataSet(entries, "Weight").apply {
                        color = android.graphics.Color.parseColor("#E91E63")
                        setCircleColor(android.graphics.Color.parseColor("#E91E63"))
                        lineWidth = 3f
                        circleRadius = 6f
                        setDrawFilled(true)
                        fillColor = android.graphics.Color.parseColor("#FCE4EC")
                        mode = LineDataSet.Mode.CUBIC_BEZIER
                    }
                    chart.data = LineData(dataSet)
                    chart.animateY(1000)
                    chart.invalidate()
                }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Enter Weight (kg)") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            leadingIcon = { Icon(Icons.Default.Scale, null) },
            trailingIcon = {
                IconButton(onClick = {
                    weight.toFloatOrNull()?.let {
                        viewModel.addGrowthRecord(it, 0f)
                        weight = ""
                    }
                }) { Icon(Icons.Default.Add, null, tint = PrimaryPink) }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VaccinationScreen(viewModel: BabyViewModel) {
    val vaccinations by viewModel.vaccinations.collectAsState(initial = emptyList())
    var showDatePicker by remember { mutableStateOf(false) }

    if (vaccinations.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize().padding(20.dp), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.Cake, null, modifier = Modifier.size(80.dp), tint = PrimaryPink)
                Spacer(modifier = Modifier.height(16.dp))
                Text("When was your baby born?", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text("This helps us calculate the exact dates for vaccinations.", textAlign = TextAlign.Center, color = Color.Gray)
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = { showDatePicker = true }, shape = RoundedCornerShape(16.dp)) {
                    Text("Select Birth Date")
                }
            }
        }
    } else {
        val completedCount = vaccinations.count { it.isDone }
        val progress = if (vaccinations.isNotEmpty()) completedCount.toFloat() / vaccinations.size else 0f

        Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
            Text("Immunization", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Black)
            Spacer(modifier = Modifier.height(8.dp))
            
            // Progress Bar
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth().height(12.dp).clip(CircleShape),
                color = PrimaryPink,
                trackColor = SoftPink
            )
            Text("$completedCount of ${vaccinations.size} vaccines completed", style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(top = 4.dp))
            
            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(vaccinations) { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth().clickable { viewModel.toggleVaccination(item) },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Timeline line
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(40.dp)) {
                            Box(modifier = Modifier.size(24.dp).clip(CircleShape).background(if(item.isDone) PrimaryPink else Color.LightGray)) {
                                if(item.isDone) Icon(Icons.Default.Check, null, modifier = Modifier.padding(4.dp), tint = Color.White)
                            }
                            Box(modifier = Modifier.width(2.dp).height(60.dp).background(Color.LightGray))
                        }
                        
                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(20.dp),
                            color = if(item.isDone) SoftPink else Color.White,
                            shadowElevation = 2.dp,
                            border = androidx.compose.foundation.BorderStroke(1.dp, if(item.isDone) PrimaryPink.copy(alpha = 0.3f) else Color.Transparent)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                    Text(item.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = if(item.isDone) PrimaryPink else Color.Black)
                                    Text(item.dateLabel, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                                }
                                Text("Prevents: ${item.disease}", style = MaterialTheme.typography.bodySmall, color = Color.DarkGray)
                                if (item.isDone) {
                                    Text("Status: Completed 🎉", style = MaterialTheme.typography.labelSmall, color = PrimaryPink, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = System.currentTimeMillis()
        )
        
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        viewModel.scheduleVaccinations(it)
                    }
                    showDatePicker = false
                }) { Text("Confirm") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
fun CareHubScreen(viewModel: BabyViewModel) {
    var careTab by remember { mutableIntStateOf(0) }
    
    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        TabRow(selectedTabIndex = careTab, containerColor = Color.Transparent, divider = {}) {
            Tab(selected = careTab == 0, onClick = { careTab = 0 }) {
                Text("Feeding", modifier = Modifier.padding(12.dp), fontWeight = if(careTab == 0) FontWeight.Bold else FontWeight.Normal)
            }
            Tab(selected = careTab == 1, onClick = { careTab = 1 }) {
                Text("Milestones", modifier = Modifier.padding(12.dp), fontWeight = if(careTab == 1) FontWeight.Bold else FontWeight.Normal)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        
        if (careTab == 0) FeedingList() else MilestoneList(viewModel)
    }
}

@Composable
fun FeedingList() {
    val sections = listOf(
        FeedingSection("0-6 Months", listOf(
            "Exclusive breastfeeding: No water, honey, or formula.",
            "Demand feeding: Feed whenever the baby shows signs of hunger.",
            "Mother: Increase calorie intake with dairy and nuts."
        )),
        FeedingSection("6-12 Months", listOf(
            "Introduce mashed banana or papaya.",
            "Start thick dalia or khichdi.",
            "Avoid sugar and salt in baby's food."
        )),
        FeedingSection("Mother's Diet", listOf(
            "Drink 10+ glasses of water for milk production.",
            "Iron & Calcium rich foods are mandatory.",
            "Avoid excessive caffeine and spices."
        ))
    )
    
    LazyColumn(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        items(sections) { section ->
            Text(section.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = PrimaryPink)
            Spacer(modifier = Modifier.height(8.dp))
            section.tips.forEach { tip ->
                Card(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), colors = CardDefaults.cardColors(containerColor = SoftMint.copy(alpha = 0.5f))) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Restaurant, null, modifier = Modifier.size(16.dp), tint = Color(0xFF2E7D32))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(tip, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

@Composable
fun MilestoneList(viewModel: BabyViewModel) {
    val milestones by viewModel.milestones.collectAsState(initial = emptyList())
    
    LaunchedEffect(Unit) { viewModel.initDefaults() }

    val categories = milestones.groupBy { it.category }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        categories.forEach { (cat, list) ->
            item {
                Text(cat, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = PrimaryBlue)
                Spacer(modifier = Modifier.height(8.dp))
                list.forEach { milestone ->
                    Surface(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp).clickable { viewModel.toggleMilestone(milestone) },
                        shape = RoundedCornerShape(16.dp),
                        color = if(milestone.isAchieved) SoftBlue else Color.White,
                        border = androidx.compose.foundation.BorderStroke(1.dp, if(milestone.isAchieved) PrimaryBlue.copy(alpha = 0.3f) else Color.Transparent)
                    ) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                if(milestone.isAchieved) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                contentDescription = null,
                                tint = if(milestone.isAchieved) PrimaryBlue else Color.Gray
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(milestone.title, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}

data class VaccineData(val name: String, val disease: String, val dueDate: String, val isDone: Boolean)
data class FeedingSection(val title: String, val tips: List<String>)
