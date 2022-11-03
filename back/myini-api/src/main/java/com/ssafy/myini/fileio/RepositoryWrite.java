package com.ssafy.myini.fileio;

import com.ssafy.myini.InitializerException;
import com.ssafy.myini.erd.response.ConditionItemDto;
import com.ssafy.myini.erd.response.ErdTableListResponse;
import com.ssafy.myini.erd.response.TableColumnDto;
import com.ssafy.myini.initializer.request.InitializerRequest;
import com.ssafy.myini.initializer.response.PreviewResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class RepositoryWrite {
        static StringBuilder repositoryImportContents;

    public static String repositoryPreview(JSONObject tableItem, InitializerRequest initializerRequest){
            repositoryImportContents = new StringBuilder();
            String pkType = "";

            StringBuilder contents = new StringBuilder();

            String tableName = (String) tableItem.get("name");

            //PK 타입 획득하기
            JSONArray columns = (JSONArray) tableItem.get("columns");
            for (int j=0 ; j<columns.size() ; j++){
                JSONObject column = (JSONObject) columns.get(j);

                JSONObject option = (JSONObject) column.get("option");
                if( (Boolean) option.get("primaryKey")){
                    pkType = (String) column.get("dataType");
                    break;
                }
            }

            pkType = dataTypeChange(pkType);

            repositoryImportContents.append("import " + initializerRequest.getSpring_package_name() + ".entity."+tableName+";\n"+
                    "import org.springframework.data.jpa.repository.JpaRepository;\n");

            contents.append("package " + initializerRequest.getSpring_package_name() + ".repository;\n" +
                    "\n"+
                    repositoryImportContents+
                    "\n"+
                    "public interface "+ tableName + "Repository extends JpaRepository<"+tableName+", "+pkType+">{}"
            ) ;

            return contents.toString();
    }

    public static void repositoryWrite(JSONObject tableItem, InitializerRequest initializerRequest){
     try {
        FileUtil.fileWrite(initializerRequest, repositoryPreview(tableItem,initializerRequest), "repository", (String) tableItem.get("name")+"Repository");
    }catch (Exception e){
        throw new InitializerException(InitializerException.INITIALIZER_FAIL);
    }
    }

    private static String dataTypeChange(String columnDataType) {
        if(columnDataType.equals("BIGINT(Long)")){
            return "Long";
        }else if(columnDataType.equals("INT(Integer)")){
            return "Integer";
        }else if(columnDataType.equals("CHAR(Character)")){
            return "Character";
        }else if(columnDataType.equals("DOUBLE(Double)")){
            return "Double";
        }else if(columnDataType.equals("FLOAT(Float)")){
            return "Float";
        }else if(columnDataType.equals("SMALLINT(Short)")){
            return "Short";
        }else if(columnDataType.equals("TINYINT(Byte)")){
            return "Byte";
        }else if(columnDataType.equals("BOOLEAN(Boolean)")){
            return "Boolean";
        }else if(columnDataType.equals("VARCHAR(String)")){
            return "String";
        }
        return "String";
    }
}
