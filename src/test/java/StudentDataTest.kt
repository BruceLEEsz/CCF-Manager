import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.junit.Before
import org.junit.Test
import java.io.File

class StudentDataTest {
    lateinit var book: HSSFWorkbook

    @Before
    fun load() {
        val ins = this.javaClass.getResourceAsStream("test_学生信息模板.xls") ?: throw NullPointerException()
        book = HSSFWorkbook(ins)
    }

    @Test
    fun read() {
        val she = book.getSheetAt(0)
        var index = 1
        while(true){
            val row  = she.getRow(index)
            if(row == null || row.getCell(0) == null || row.getCell(0).toString().isEmpty()){
                break
            }
            println(row.getCell(0).toString())
            println(row.getCell(1).toString())
            println(row.getCell(2).toString())
            println(row.getCell(3).toString())
            index++
        }
    }
}