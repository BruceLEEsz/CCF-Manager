import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.inter.impl.Userimpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class SQLTest {

    @Test
    public void test() {
        SQLManager.init();
        Userimpl insert=new Userimpl();
        insert.selectAllUser();
        //insert.insertUser();
    }

}
