package plc.compiler;

import java.io.PrintWriter;

public final class Generator implements Ast.Visitor<Void> {

    private final PrintWriter writer;
    private int indent = 0;

    public Generator(PrintWriter writer) {
        this.writer = writer;
    }

    private void print(Object... objects) {
        for (Object object : objects) {
            if (object instanceof Ast) {
                visit((Ast) object);
            } else {
                writer.write(object.toString());
            }
        }
    }

    private void newline(int indent) {
        writer.println();
        for (int i = 0; i < indent; i++) {
            writer.write("    ");
        }
    }

    @Override
    public Void visit(Ast.Source ast) {

        print("public final class Main {");
        newline(indent);
        indent++;
        newline(indent);

        print("public static void main(String[] args) {");
        indent++;
        newline(indent);

        for (int i = 0; i < ast.getStatements().size(); i++) {
            Ast.Statement statement = ast.getStatements().get(i);

            visit(statement);

            print(";");
            if (i == ast.getStatements().size()-1){
                indent--;
            }
            newline(indent);
        }

        print("}");
        indent--;
        newline(indent);
        newline(indent);
        print("}");
        newline(indent);

        return null;
    }

    @Override
    public Void visit(Ast.Statement.Expression ast) {
        visit(ast.getExpression());
        return null;
    }

    @Override
    public Void visit(Ast.Statement.Declaration ast) {

        // TODO:  Generate Java to handle Declaration node.

        return null;
    }

    @Override
    public Void visit(Ast.Statement.Assignment ast) {

        // TODO:  Generate Java to handle Assignment node.

        return null;
    }

    @Override
    public Void visit(Ast.Statement.If ast) {

        // TODO:  Generate Java to handle If node.

        return null;
    }

    @Override
    public Void visit(Ast.Statement.While ast) {

        // TODO:  Generate Java to handle While node.

        return null;
    }

    @Override
    public Void visit(Ast.Expression.Literal ast) {

        if (ast.getValue() instanceof String){
            print("\"", ast.getValue(), "\"");
        }
        else {
            print(ast.getValue());
        }

        return null;
    }

    @Override
    public Void visit(Ast.Expression.Group ast) {

        // TODO:  Generate Java to handle Group node.

        return null;
    }

    @Override
    public Void visit(Ast.Expression.Binary ast) {

        // TODO:  Generate Java to handle Binary node.

        return null;
    }

    @Override
    public Void visit(Ast.Expression.Variable ast) {

        // TODO:  Generate Java to handle Variable node.

        return null;
    }

    @Override
    public Void visit(Ast.Expression.Function ast) {

        String name = ast.getName();
        print(name, "(");
        int count = 0;
        for (Ast.Expression exp: ast.getArguments()){
            if (count > 0){
                print(", ");
            }
            visit(exp);
            count++;
        }
        print(")");

        return null;
    }

}
