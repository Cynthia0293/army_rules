import os
import json

# Load the JSON configuration
with open(r'd:\Code\Javacode\army_rules\src\main\java\avoid_double_equals_for_objects\0\avoid_double_equals_for_objects.json', 'r', encoding='utf-8') as f:
    config = json.load(f)

# Test factor mappings
object_types = {item['value']: item['description'] for item in config['test_factors'][0]['values']}
operators = {item['value']: item['description'] for item in config['test_factors'][1]['values']}
sources = {item['value']: item['description'] for item in config['test_factors'][2]['values']}
contexts = {item['value']: item['description'] for item in config['test_factors'][3]['values']}

# Module numbers
modules = {
    'compared_object_type': list(range(101, 139)),  # 38 types
    'comparison_operator': [201, 202],  # 2 operators
    'instantiation_source': list(range(301, 307)),  # 6 sources
    'syntax_context': list(range(401, 410))  # 9 contexts
}

def generate_main_java(factor_name, factor_value, module_num):
    # Base template
    template = '''package org.example;

{imports}
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：{violation_desc}
 * 测试意图：覆盖{factor_desc}
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {{
    /**
     * 主方法，执行程序入口。
     * @param args 命令行参数
     */
    public static void main(String[] args) {{
        {code}
    }}
}}
'''

    if factor_name == 'compared_object_type':
        violation_desc = f"在 if 条件中使用 \"==\" 比较 {object_types[factor_value]} 对象引用"
        factor_desc = f"比较对象类型：{object_types[factor_value]}"
        code, imports = generate_object_comparison_code(factor_value)
    elif factor_name == 'comparison_operator':
        violation_desc = f"在 if 条件中使用 \"{operators[factor_value]}\" 比较 String 对象引用"
        factor_desc = f"比较操作符：{operators[factor_value]}"
        code, imports = generate_operator_comparison_code(factor_value)
    elif factor_name == 'instantiation_source':
        violation_desc = f"在 if 条件中使用 \"==\" 比较通过 {sources[factor_value]} 获取的 String 对象引用"
        factor_desc = f"实例化来源：{sources[factor_value]}"
        code, imports = generate_source_comparison_code(factor_value)
    elif factor_name == 'syntax_context':
        violation_desc = f"在 {contexts[factor_value]} 使用 \"==\" 比较 String 对象引用"
        factor_desc = f"语法上下文：{contexts[factor_value]}"
        code, imports = generate_context_comparison_code(factor_value)

    return template.format(imports=imports, violation_desc=violation_desc, factor_desc=factor_desc, code=code)

def generate_object_comparison_code(obj_type):
    imports = ""
    if obj_type == 'string':
        code = '''String str1 = new String("test"); // 测试因子(compared_object_type=string)
        String str2 = new String("test");
        if (str1 == str2) { // 检查点
            log.info("viol");
        }'''
    elif obj_type == 'string_buffer':
        code = '''StringBuffer sb1 = new StringBuffer("test"); // 测试因子(compared_object_type=string_buffer)
        StringBuffer sb2 = new StringBuffer("test");
        if (sb1 == sb2) { // 检查点
            log.info("viol");
        }'''
    elif obj_type == 'string_builder':
        code = '''StringBuilder sb1 = new StringBuilder("test"); // 测试因子(compared_object_type=string_builder)
        StringBuilder sb2 = new StringBuilder("test");
        if (sb1 == sb2) { // 检查点
            log.info("viol");
        }'''
    elif obj_type.startswith('wrapper_'):
        wrapper_type = obj_type.split('_')[1].capitalize()
        if wrapper_type == 'Character':
            code = f'''{wrapper_type} c1 = 'a'; // 测试因子(compared_object_type={obj_type})
        {wrapper_type} c2 = 'a';
        if (c1 == c2) {{ // 检查点
            log.info("viol");
        }}'''
        else:
            code = f'''{wrapper_type} num1 = 1; // 测试因子(compared_object_type={obj_type})
        {wrapper_type} num2 = 1;
        if (num1 == num2) {{ // 检查点
            log.info("viol");
        }}'''
    elif obj_type == 'big_integer':
        imports = "import java.math.BigInteger;"
        code = '''BigInteger bi1 = new BigInteger("123"); // 测试因子(compared_object_type=big_integer)
        BigInteger bi2 = new BigInteger("123");
        if (bi1 == bi2) { // 检查点
            log.info("viol");
        }'''
    elif obj_type == 'big_decimal':
        imports = "import java.math.BigDecimal;"
        code = '''BigDecimal bd1 = new BigDecimal("1.23"); // 测试因子(compared_object_type=big_decimal)
        BigDecimal bd2 = new BigDecimal("1.23");
        if (bd1 == bd2) { // 检查点
            log.info("viol");
        }'''
    elif obj_type == 'util_date':
        imports = "import java.util.Date;"
        code = '''Date date1 = new Date(); // 测试因子(compared_object_type=util_date)
        Date date2 = new Date();
        if (date1 == date2) { // 检查点
            log.info("viol");
        }'''
    elif obj_type.startswith('time_'):
        imports = "import java.time.*;"
        time_class = obj_type.split('_', 1)[1].replace('_', '').capitalize()
        code = f'''{time_class} time1 = {time_class}.now(); // 测试因子(compared_object_type={obj_type})
        {time_class} time2 = {time_class}.now();
        if (time1 == time2) {{ // 检查点
            log.info("viol");
        }}'''
    elif obj_type == 'uuid':
        imports = "import java.util.UUID;"
        code = '''UUID uuid1 = UUID.randomUUID(); // 测试因子(compared_object_type=uuid)
        UUID uuid2 = UUID.randomUUID();
        if (uuid1 == uuid2) { // 检查点
            log.info("viol");
        }'''
    elif obj_type == 'optional':
        imports = "import java.util.Optional;"
        code = '''Optional<String> opt1 = Optional.of("test"); // 测试因子(compared_object_type=optional)
        Optional<String> opt2 = Optional.of("test");
        if (opt1 == opt2) { // 检查点
            log.info("viol");
        }'''
    elif obj_type in ['array_list', 'linked_list', 'hash_set', 'tree_set', 'hash_map', 'tree_map']:
        imports = "import java.util.*;"
        collection_class = obj_type.replace('_', ' ').title().replace(' ', '')
        code = f'''{collection_class} coll1 = new {collection_class}(); // 测试因子(compared_object_type={obj_type})
        {collection_class} coll2 = new {collection_class}();
        if (coll1 == coll2) {{ // 检查点
            log.info("viol");
        }}'''
    elif obj_type.startswith('array_primitive_'):
        primitive_type = obj_type.split('_')[-1]
        code = f'''{primitive_type}[] arr1 = new {primitive_type}[1]; // 测试因子(compared_object_type={obj_type})
        {primitive_type}[] arr2 = new {primitive_type}[1];
        if (arr1 == arr2) {{ // 检查点
            log.info("viol");
        }}'''
    elif obj_type.startswith('array_object_'):
        if obj_type == 'array_object_string':
            code = '''String[] arr1 = new String[1]; // 测试因子(compared_object_type=array_object_string)
        String[] arr2 = new String[1];
        if (arr1 == arr2) { // 检查点
            log.info("viol");
        }'''
        elif obj_type == 'array_object_integer':
            code = '''Integer[] arr1 = new Integer[1]; // 测试因子(compared_object_type=array_object_integer)
        Integer[] arr2 = new Integer[1];
        if (arr1 == arr2) { // 检查点
            log.info("viol");
        }'''
        else:
            code = '''Object[] arr1 = new Object[1]; // 测试因子(compared_object_type=array_object_general)
        Object[] arr2 = new Object[1];
        if (arr1 == arr2) { // 检查点
            log.info("viol");
        }'''
    elif obj_type == 'custom_pojo':
        code = '''class Person {
            String name;
            Person(String name) { this.name = name; }
        }
        Person p1 = new Person("test"); // 测试因子(compared_object_type=custom_pojo)
        Person p2 = new Person("test");
        if (p1 == p2) { // 检查点
            log.info("viol");
        }'''
    return code, imports

def generate_operator_comparison_code(operator):
    imports = ""
    if operator == 'equals':
        code = '''String str1 = new String("test");
        String str2 = new String("test");
        if (str1 == str2) { // 检查点, 测试因子(comparison_operator=equals)
            log.info("viol");
        }'''
    else:
        code = '''String str1 = new String("test");
        String str2 = new String("different");
        if (str1 != str2) { // 检查点, 测试因子(comparison_operator=not_equals)
            log.info("viol");
        }'''
    return code, imports

def generate_source_comparison_code(source):
    imports = ""
    if source == 'explicit_new':
        code = '''String str1 = new String("test"); // 测试因子(instantiation_source=explicit_new)
        String str2 = new String("test");
        if (str1 == str2) { // 检查点
            log.info("viol");
        }'''
    elif source == 'autoboxing':
        code = '''Integer num1 = 1; // 测试因子(instantiation_source=autoboxing)
        Integer num2 = 1;
        if (num1 == num2) { // 检查点
            log.info("viol");
        }'''
    elif source == 'static_factory':
        code = '''String str1 = String.valueOf("test"); // 测试因子(instantiation_source=static_factory)
        String str2 = String.valueOf("test");
        if (str1 == str2) { // 检查点
            log.info("viol");
        }'''
    elif source == 'clone':
        code = '''class CloneableData implements Cloneable {
            String data;
            CloneableData(String data) { this.data = data; }
            @Override
            public Object clone() throws CloneNotSupportedException {
                return super.clone();
            }
        }
        try {
            CloneableData obj1 = new CloneableData("test"); // 测试因子(instantiation_source=clone)
            CloneableData obj2 = (CloneableData) obj1.clone();
            if (obj1 == obj2) { // 检查点
                log.info("viol");
            }
        } catch (CloneNotSupportedException e) {
            log.error("Clone failed", e);
        }'''
    elif source == 'deserialization':
        code = '''// Note: Deserialization would require complex setup, using simplified version
        String str1 = "test"; // 测试因子(instantiation_source=deserialization)
        String str2 = "test";
        if (str1 == str2) { // 检查点
            log.info("viol");
        }'''
    elif source == 'reflection':
        code = '''try {
            String str1 = String.class.newInstance(); // 测试因子(instantiation_source=reflection)
            String str2 = String.class.newInstance();
            if (str1 == str2) { // 检查点
                log.info("viol");
            }
        } catch (Exception e) {
            log.error("Reflection failed", e);
        }'''
    return code, imports

def generate_context_comparison_code(context):
    imports = ""
    base_code = '''String str1 = new String("test");
        String str2 = new String("test");'''

    if context == 'if_condition':
        code = f'''{base_code}
        if (str1 == str2) {{ // 检查点, 测试因子(syntax_context=if_condition)
            log.info("viol");
        }}'''
    elif context == 'while_condition':
        code = f'''{base_code}
        while (str1 == str2) {{ // 检查点, 测试因子(syntax_context=while_condition)
            log.info("viol");
            break;
        }}'''
    elif context == 'do_while_condition':
        code = f'''{base_code}
        do {{
            log.info("loop");
        }} while (str1 == str2); // 检查点, 测试因子(syntax_context=do_while_condition)'''
    elif context == 'for_condition':
        code = f'''{base_code}
        for (int i = 0; str1 == str2; i++) {{ // 检查点, 测试因子(syntax_context=for_condition)
            log.info("viol");
            break;
        }}'''
    elif context == 'ternary_condition':
        code = f'''{base_code}
        boolean result = (str1 == str2) ? true : false; // 检查点, 测试因子(syntax_context=ternary_condition)
        if (result) {{
            log.info("viol");
        }}'''
    elif context == 'variable_assignment':
        code = f'''{base_code}
        boolean flag = (str1 == str2); // 检查点, 测试因子(syntax_context=variable_assignment)
        if (flag) {{
            log.info("viol");
        }}'''
    elif context == 'return_statement':
        code = f'''{base_code}
        return (str1 == str2); // 检查点, 测试因子(syntax_context=return_statement)'''
    elif context == 'method_argument':
        code = f'''{base_code}
        log.info("result: " + (str1 == str2)); // 检查点, 测试因子(syntax_context=method_argument)'''
    elif context == 'lambda_body':
        code = f'''{base_code}
        Runnable r = () -> {{
            if (str1 == str2) {{ // 检查点, 测试因子(syntax_context=lambda_body)
                log.info("viol");
            }}
        }};
        r.run();'''
    return code, imports

def main():
    root_dir = r'd:\Code\Javacode\army_rules\src\main\java\avoid_double_equals_for_objects'

    # Generate for each factor
    factor_index = 0
    for factor in config['test_factors']:
        factor_name = factor['name']
        factor_index += 1

        for value_index, value in enumerate(factor['values'], 1):
            module_num = factor_index * 100 + value_index
            module_dir = f"{root_dir}\\{module_num}\\avoidDoubleEqualsForObjects{module_num}\\src\\main\\java\\org\\example"
            os.makedirs(module_dir, exist_ok=True)

            file_path = os.path.join(module_dir, 'Main.java')
            content = generate_main_java(factor_name, value['value'], module_num)
            with open(file_path, 'w', encoding='utf-8') as f:
                f.write(content)
            print(f"Generated: {file_path}")

if __name__ == "__main__":
    main()