package core;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import soot.MethodOrMethodContext;
import soot.Scene;
import soot.SootMethod;
import soot.jimple.infoflow.android.InfoflowAndroidConfiguration;
import soot.jimple.infoflow.android.SetupApplication;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.util.dot.DotGraph;
import soot.util.queue.QueueReader;

public class App {

    public static void main(String[] args) {
        String androidPlatform = "D:/bishe/apk/android.jar";
        String appName = "tomato";
        File file = new File("D:/bishe/apk/" + appName + ".apk");
        String appToRun = file.getAbsolutePath();

        InfoflowAndroidConfiguration config = new InfoflowAndroidConfiguration();
        config.getAnalysisFileConfig().setAndroidPlatformDir(androidPlatform);
        config.getAnalysisFileConfig().setTargetAPKFile(appToRun);
        config.setMergeDexFiles(true);

        SetupApplication analyzer = new SetupApplication(config);
        analyzer.constructCallgraph();
        CallGraph cg = Scene.v().getCallGraph();

        DotGraph dot = new DotGraph("callgraph");
        analyzeCG(dot, cg);
        String destination = "D:/bishe/dot/" + appName + ".dot";
        dot.plot(destination);
        System.out.println(destination);
    }

    public static void analyzeCG(DotGraph dot, CallGraph cg) {
        QueueReader<Edge> edges = cg.listener();
        Set<String> visited = new HashSet<>();

        while (edges.hasNext()) {
            Edge edge = edges.next();
            SootMethod target = (SootMethod) edge.getTgt();
            MethodOrMethodContext src = edge.getSrc();
            if (!visited.contains(src.toString())) {
                dot.drawNode(src.toString());
                visited.add(src.toString());
            }
            if (!visited.contains(target.toString())) {
                dot.drawNode(target.toString());
                visited.add(target.toString());
            }
            dot.drawEdge(src.toString(), target.toString());
        }
    }
}

