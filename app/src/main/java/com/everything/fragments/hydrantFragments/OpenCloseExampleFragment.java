package com.everything.fragments.hydrantFragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.everything.R;
import com.everything.Utils;
import com.everything.fragments.BaseFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

/**
 * Created by Mirek on 2016-03-18.
 */
public class OpenCloseExampleFragment extends BaseFragment {

    public static final int BAD_VALUE = -1;
    @Bind(R.id.items_types)
    Spinner figureSpinner;
    @Bind(R.id.rectangle_panel)
    LinearLayout rectanglePanel;
    @Bind(R.id.triangle_panel)
    LinearLayout trianglePanel;
    @Bind(R.id.circle_panel)
    LinearLayout circlePanel;
    @Bind(R.id.count_of_figures_et)
    EditText countOfFiguresField;
    @Bind(R.id.total_area_value_tv)
    TextView totalAreaValueTv;
    @Bind(R.id.rect_width)
    EditText rectWidth;
    @Bind(R.id.rect_height)
    EditText rectHeight;
    @Bind(R.id.circle_radius)
    EditText circleRadius;
    @Bind(R.id.triangle_base)
    EditText triangleBase;
    @Bind(R.id.triangle_height)
    EditText triangleHeight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.fragmentLayout = R.layout.open_close_example_fragment;
        View view = inflater.inflate(fragmentLayout, container, false);
        ButterKnife.bind(this, view);
        super.initViews(view);
        return view;
    }

    @OnItemSelected(R.id.items_types)
    void onFigureSpinnerClicked(int position) {
        new PanelsVisibilityChanger().doProperThingsDependsOnMode(position);
    }

    @OnClick(R.id.calculate_area)
    void onAreaCalculated() {
        int countFigures = readCountOfFigures();
        preapareResults(countFigures);
    }

    private int readCountOfFigures() {
        String countOfFigures = countOfFiguresField.getText().toString();
        if(Utils.validateEdittext(countOfFiguresField, "Please enter count of figures!")) {
            return Integer.parseInt(countOfFigures);
        }else{
            return BAD_VALUE;
        }
    }

    private void preapareResults(int count) {
        if(count!= BAD_VALUE) {
            int selectedItemPosition = figureSpinner.getSelectedItemPosition();
            boolean validationResult = validateFields(selectedItemPosition);

            if(validationResult) {
                ArrayList<Shape> shapes = prepareNewShapes(count, selectedItemPosition);
                double totalArea = new AreaManager().calculateArea(shapes);
                totalAreaValueTv.setText(String.valueOf(totalArea));
            }
        }
    }

    private boolean validateFields(int selectedItemPosition) {
        FieldsValidator fieldsValidator =new FieldsValidator();
        fieldsValidator.doProperThingsDependsOnMode(selectedItemPosition);
        return fieldsValidator.getValidationResult();
    }

    private ArrayList<Shape> prepareNewShapes(int count, int selectedItemPosition) {
        NewShapesAdder newShapesAdder = new NewShapesAdder(count);
        newShapesAdder.doProperThingsDependsOnMode(selectedItemPosition);
        return newShapesAdder.getShapes();
    }

    class FieldsValidator extends ModesChanger{

        private boolean validationResult = false;

        public FieldsValidator(){
            validationResult = false;
        }

        @Override
        void onRectangleMode() {
            validationResult =
            Utils.validateEdittext(rectWidth,"Please enter rectangle width") &&
            Utils.validateEdittext(rectHeight,"Please enter rectangle height");
        }

        @Override
        void onCircleMode() {
            validationResult =
            Utils.validateEdittext(circleRadius,"Please enter circle radius");
        }

        @Override
        void onTriangleMode() {
            validationResult =
            Utils.validateEdittext(triangleBase,"Please enter triangle base") &&
            Utils.validateEdittext(triangleHeight,"Please enter triangle height");
        }

        public boolean getValidationResult(){
            return validationResult;
        }

    }

    class PanelsVisibilityChanger extends ModesChanger {

        @Override
        void onRectangleMode() {
            setCalculatingPanelsVisibility(View.VISIBLE, View.GONE, View.GONE);
        }

        @Override
        void onCircleMode() {
            setCalculatingPanelsVisibility(View.GONE, View.GONE, View.VISIBLE);
        }

        @Override
        void onTriangleMode() {
            setCalculatingPanelsVisibility(View.GONE, View.VISIBLE, View.GONE);
        }

        private void setCalculatingPanelsVisibility(final int rectangleVisibility, final int triangleVisibility, final int circleVisibility) {
            rectanglePanel.setVisibility(rectangleVisibility);
            trianglePanel.setVisibility(triangleVisibility);
            circlePanel.setVisibility(circleVisibility);
        }

    }

    class NewShapesAdder extends ModesChanger {

        private final int COUNT_OF_FIGURES;

        private final ArrayList<Shape> shapes;

        public NewShapesAdder(int count) {
            this.COUNT_OF_FIGURES = count;
            this.shapes = new ArrayList<>(COUNT_OF_FIGURES);
        }

        @Override
        void onRectangleMode() {
            int width = Utils.getIntValueFromEdittext(rectWidth);
            int height = Utils.getIntValueFromEdittext(rectHeight);

            addNewShape(new Rectangle(width, height));
        }

        @Override
        void onCircleMode() {
            int radius = Utils.getIntValueFromEdittext(circleRadius);

            addNewShape(new Circle(radius));
        }

        @Override
        void onTriangleMode() {
            int base = Utils.getIntValueFromEdittext(triangleBase);
            int trHeight = Utils.getIntValueFromEdittext(triangleHeight);

            addNewShape(new Triangle(base, trHeight));
        }

        private void addNewShape(Shape shape) {
            for (int i = 0; i < COUNT_OF_FIGURES; i++) {
                shapes.add(shape);
            }
        }

        public ArrayList<Shape> getShapes() {
            return shapes;
        }
    }

    class Rectangle implements Shape {
        private double width;
        private double height;

        public Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public double getArea() {
            return width * height;
        }
    }

    class Circle implements Shape {
        private double radius;

        public Circle(double radius) {
            this.radius = radius;
        }

        @Override
        public double getArea() {
            return Math.PI * Math.pow(radius, 2);
        }
    }

    class Triangle implements Shape {
        private double base;
        private double height;

        public Triangle(double base, double height) {
            this.base = base;
            this.height = height;
        }

        @Override
        public double getArea() {
            return 0.5 * base * height;
        }
    }

    class AreaManager {
        public double calculateArea(ArrayList<Shape> shapes) {
            double totalArea = 0.0;
            for (Shape shape : shapes) {
                totalArea += shape.getArea();
            }
            return totalArea;
        }
    }

    interface Shape {
        double getArea();
    }

    abstract class ModesChanger {

        private ModesChanger() {
        }

        public void doProperThingsDependsOnMode(int position) {
            switch (position) {
                case 0:
                    onRectangleMode();
                    break;
                case 1:
                    onCircleMode();
                    break;
                case 2:
                    onTriangleMode();
                    break;
            }
        }

        abstract void onRectangleMode();

        abstract void onCircleMode();

        abstract void onTriangleMode();
    }
}
