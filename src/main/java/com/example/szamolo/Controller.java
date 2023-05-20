package com.example.szamolo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.scene.input.MouseEvent;
import org.json.JSONArray;
import org.json.simple.JSONObject;


public class Controller {
    JSONObject ans = new JSONObject();
    JSONArray arr = new JSONArray();

    public boolean clearafterequals = true;

    String eredmenystr = "";
    String collector = "";
    @FXML private Label eredmenylabel;
    @FXML private Button Ans;
    @FXML private Button nextans;
    @FXML private Button prevans;
    float finalnum = 0;
    String operator = "";
    int ansselector = -1;


    public void onNumberClicked(ActionEvent event) {
        if(clearafterequals == true){
            eredmenystr = "";
            eredmenylabel.setText(eredmenystr);
            clearafterequals = false;
        }
        eredmenystr = eredmenystr + ((Button)event.getSource()).getText();
        eredmenylabel.setText(eredmenystr);
        collector = collector + ((Button)event.getSource()).getText();

    }

    public void onSymbolClicked(ActionEvent event) throws IOException {
        String sym = ((Button)event.getSource()).getText();
        System.out.println(sym);

        switch (sym){
            case "C": {
                eredmenystr = "";
                collector = "0";
                eredmenylabel.setText(collector);
                finalnum = 0;
                break;
            }

            case "Ans":{

                String read = reader(0);
                System.out.println(reader(0));
                eredmenystr = (read);
                collector = "" + (read);
                eredmenylabel.setText(read);
                break;

            }

            case ".":{
                collector = collector + ".";
                break;
            }

            case "+":{
                collector = collector + " + ";
                eredmenystr = "";
                break;
            }

            case "-":{
                collector = collector + " - ";
                eredmenystr = "";
                break;
            }
            case "*":{
                collector = collector + " * ";
                eredmenystr = "";
                break;
            }

            case "/":{
                collector = collector + " / ";
                eredmenystr = "";
                break;
            }

            case "+/-":{
                collector.replaceAll("0333", "666");
                System.out.println(collector);
                break;
            }

            case "=":{
                System.out.println(collector);
                String[] tokens = collector.split(" ");
                finalnum = Float.parseFloat(tokens[0]);


                for(int i = 2; i < tokens.length; i=i+2){
                    operator = tokens[i-1];
                    switch (operator){
                        case "+":{
                            finalnum = finalnum + Float.parseFloat(tokens[i]);
                            break;
                        }

                        case "-":{
                            finalnum = finalnum - Float.parseFloat(tokens[i]);
                            break;
                        }
                        case "*":{
                            finalnum = finalnum * Float.parseFloat(tokens[i]);
                            break;
                        }

                        case "/":{
                            finalnum = finalnum / Float.parseFloat(tokens[i]);
                            break;
                        }

                        default: break;
                    }

                }
                System.out.println(finalnum);
                eredmenylabel.setText(String.valueOf(finalnum));
                ans.put("ans", finalnum);
                arr.put(ans);

                FileWriter file = new FileWriter("ANS.json");
                file.write(arr + "\n");
                file.close();

                clearafterequals = true;
                collector = String.valueOf(finalnum);
                ansselector = -1;

                break;

            }
            default: break;
        }
    }

    public void tt() throws IOException {
        Tooltip tt = new Tooltip(reader(0));
        Ans.setTooltip(tt);
        System.out.println(reader(0));
        System.out.println(collector);
    }

    public String reader(int n) throws IOException {
        String line = new String(Files.readAllBytes(Path.of("ANS.json")));
        String[] tokens = line.split(",");
        String ansvalue = tokens[tokens.length+ansselector+n];
        ansvalue = ansvalue.replace("[{\"ans\":","");
        ansvalue = ansvalue.replace("{\"ans\":","");
        ansvalue = ansvalue.replace("}]","");
        ansvalue = ansvalue.replace("}","");
        return ansvalue;
    }

    public void prevans(ActionEvent event) {
        ansselector--;
        System.out.println(ansselector);
    }

    public void nextans(ActionEvent event) {
        ansselector++;
        System.out.println(ansselector);
    }

    public void ttn(MouseEvent mouseEvent) throws IOException {
        Tooltip tt = new Tooltip(reader(1));
        nextans.setTooltip(tt);
    }

    public void ttp(MouseEvent mouseEvent) throws IOException {
        Tooltip tt = new Tooltip(reader(-1));
        prevans.setTooltip(tt);
    }
}