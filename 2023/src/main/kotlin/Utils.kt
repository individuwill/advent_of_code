class Utils {
    companion object Static {
        @JvmStatic
//            private val sampleResourceName = this::class.simpleName?.replace("Test", ".sample.txt")!!.lowercase()
//            private val sampleInput = this::class.java.classLoader.getResource(sampleResourceName)!!.readText()
//            private val inputResourceName = this::class.simpleName?.replace("Test", ".txt")!!.lowercase()
//            private val input = this::class.java.classLoader.getResource(inputResourceName)?.readText()!!.trim()

        fun getResource(resourceName: String): String {
            val text = this::class.java.classLoader.getResource(resourceName)?.readText()!!.trim()
            return text
        }
    }
}