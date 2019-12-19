import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import java.io.File
import java.io.FileOutputStream

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


    val file = File("test.xlsx")
    @Before
    @Ignore
    fun deleteFile(){
        file.delete()
        file.createNewFile()
    }
    @Test
    @Ignore
    fun createXLS(){
        val book = XSSFWorkbook()
        val sheet = book.createSheet()
        val r = sheet.createRow(0)
        r.createCell(0).setCellValue("hello world")

        val fo = FileOutputStream(file)
        book.write(fo)
        fo.flush()
        fo.close()
    }
}