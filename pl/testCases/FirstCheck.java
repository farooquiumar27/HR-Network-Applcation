import com.thinking.machines.hr.pl.model.*;
import java.awt.*;
import javax.swing.*;
public class FirstCheck extends JFrame
{
public FirstCheck( )
{
DesignationModel model=new DesignationModel( );
JTable table=new JTable(model);
JScrollPane jsp;
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
Container container=getContentPane( );
container.setLayout(new BorderLayout( ));
container.add(jsp);
setLocation(400,500);
setSize(400,500);
setVisible(true);
}
}
class main
{
public static void main(String gg[ ])
{
FirstCheck fc=new FirstCheck( );
}
};