package skalman

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SkalmanTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    CalendarScreen()
                }
            }
        }
    }
}