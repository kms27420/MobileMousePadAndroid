package com.example.kwon.mobiletouchpad.custom_view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 상하좌우 비율에 맞는 여백을 갖는 LinearLayout
 * Created by Kwon on 2018-04-16.
 */

public class PaddingLinearLayout extends LinearLayout {
    private final int LEFT_IDX = 0;
    private final int TOP_IDX = 1;
    private final int RIGHT_IDX = 2;
    private final int BOTTOM_IDX = 3;

    private final int HORI_CENTER_IDX = 0;
    private final int VERT_CENTER_IDX = 1;

    private final View[] VIEWS_FOR_PADDING = new View[4];   // 여백 뷰
    private final LayoutParams[] PARAMS_FOR_PADDING = new LayoutParams[4];  // 여백 뷰의 Params
    private final LayoutParams[] PARAMS_FOR_CENTER = new LayoutParams[2];   // 중앙 정렬되는 뷰의 Params
    private final float[] RATE = new float[4];  // 여백 비율

    public final LinearLayout MAIN_VIEW_GROUP; //  본 LinearLayout의 메인 ViewGroup이다.
    /**
     * 기본 비율(0,0,0,0) 즉, 여백이 없는 LinearLayout을 생성한다.
     */
    public PaddingLinearLayout(Context context) {
        this(context,0,0,0,0);
    }
    /**
     * 비율에 맞는 여백을 갖는 LinearLayout을 생성한다.
     * @param leftRate 왼쪽 여백 비율, leftRate>=0 && leftRate<=1 && leftRate+rightRate<=1
     * @param topRate 위쪽 여백 비율, topRate>=0 && topRate<=1 && topRate+bottomRate<=1
     * @param rightRate 오른쪽 여백 비율, rightRate>=0 && rightRate<=1 && rightRate+leftRate<=1
     * @param bottomRate 아래쪽 여백 비율, bottomRate>=0 && bottomRate<=1 && bottomRate+topRate<=1
     */
    public PaddingLinearLayout(Context context, float leftRate, float topRate, float rightRate, float bottomRate) {
        super(context);
        MAIN_VIEW_GROUP = new LinearLayout(context);    // MAIN_VIEW_GROUP 초기화
        initData();
        setPadding(leftRate, topRate, rightRate, bottomRate);
    }

    /**
     * 비율에 맞는 여백을 가진 LinearLayout에 view를 add하여 생성한다.
     * @param view add할 View
     * @param leftRate 왼쪽 여백 비율, leftRate>=0 && leftRate<=1 && leftRate+rightRate<=1
     * @param topRate 위쪽 여백 비율, topRate>=0 && topRate<=1 && topRate+bottomRate<=1
     * @param rightRate 오른쪽 여백 비율, rightRate>=0 && rightRate<=1 && rightRate+leftRate<=1
     * @param bottomRate 아래쪽 여백 비율, bottomRate>=0 && bottomRate<=1 && bottomRate+topRate<=1
     */
    public PaddingLinearLayout(View view, float leftRate, float topRate, float rightRate, float bottomRate) {
        this(view.getContext(), leftRate, topRate, rightRate, bottomRate);
        MAIN_VIEW_GROUP.setWeightSum(1);
        MAIN_VIEW_GROUP.setOrientation(VERTICAL);
        MAIN_VIEW_GROUP.addView(view, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        updateMainViewGroup();
    }

    @Deprecated
    @Override
    public final void setPadding(int left, int top, int right, int bottom) {setPadding((float)left, (float)top, (float)right, (float)bottom);}

    /**
     * 여백 비율을 설정하는 매서드
     * @param leftRate 왼쪽 여백 비율, leftRate>=0 && leftRate<=1 && leftRate+rightRate<=1
     * @param topRate 위쪽 여백 비율, topRate>=0 && topRate<=1 && topRate+bottomRate<=1
     * @param rightRate 오른쪽 여백 비율, rightRate>=0 && rightRate<=1 && rightRate+leftRate<=1
     * @param bottomRate 아래쪽 여백 비율, bottomRate>=0 && bottomRate<=1 && bottomRate+topRate<=1
     */
    public void setPadding(float leftRate, float topRate, float rightRate, float bottomRate) {
        if(!isValid(leftRate, topRate, rightRate, bottomRate))  return;
        RATE[LEFT_IDX] = leftRate;
        RATE[TOP_IDX] = topRate;
        RATE[RIGHT_IDX] = rightRate;
        RATE[BOTTOM_IDX] = bottomRate;
    }

    private boolean isValid(float leftRate, float topRate, float rightRate, float bottomRate) {
        int leftWeight = (int)(leftRate*100);
        int topWeight = (int)(topRate*100);
        int rightWeight = (int)(rightRate*100);
        int bottomWeight = (int)(bottomRate*100);
        boolean isAllPositive = (leftWeight | topWeight | rightWeight | bottomWeight) >= 0;
        boolean isInRange = leftWeight+rightWeight<=100 && topWeight+bottomWeight<=100;
        return isAllPositive && isInRange;
    }

    private void initData() {
        for(int i=0; i<VIEWS_FOR_PADDING.length; i++)   VIEWS_FOR_PADDING[i] = new View(getContext());

        for(int i=0; i<PARAMS_FOR_PADDING.length; i++) {
            if(i%2 == 0)     PARAMS_FOR_PADDING[i] = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            else    PARAMS_FOR_PADDING[i] = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        }

        for(int i=0; i<PARAMS_FOR_CENTER.length; i++) {
            if(i%2==0)  PARAMS_FOR_CENTER[i] = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            else    PARAMS_FOR_CENTER[i] = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        }
    }

    private void updateMainViewGroup() {
        super.removeAllViews();
        super.setOrientation(VERTICAL);
        super.setWeightSum(100);

        PARAMS_FOR_PADDING[TOP_IDX].weight = (int)(RATE[TOP_IDX]*100);
        PARAMS_FOR_PADDING[BOTTOM_IDX].weight = (int)(RATE[BOTTOM_IDX]*100);
        PARAMS_FOR_CENTER[VERT_CENTER_IDX].weight = 100 - PARAMS_FOR_PADDING[TOP_IDX].weight - PARAMS_FOR_PADDING[BOTTOM_IDX].weight;

        super.addView(VIEWS_FOR_PADDING[TOP_IDX], -1, PARAMS_FOR_PADDING[TOP_IDX]);
        super.addView(getCenterViewGroup(), -1, PARAMS_FOR_CENTER[VERT_CENTER_IDX]);
        super.addView(VIEWS_FOR_PADDING[BOTTOM_IDX], -1, PARAMS_FOR_PADDING[BOTTOM_IDX]);
    }

    private LinearLayout centerViewGroup;
    private View getCenterViewGroup() {
        if(centerViewGroup == null)  centerViewGroup = new LinearLayout(getContext());
        centerViewGroup.removeAllViews();
        centerViewGroup.setOrientation(HORIZONTAL);
        centerViewGroup.setWeightSum(100);
        PARAMS_FOR_PADDING[LEFT_IDX].weight = (int)(RATE[LEFT_IDX]*100);
        PARAMS_FOR_PADDING[RIGHT_IDX].weight = (int)(RATE[RIGHT_IDX]*100);
        PARAMS_FOR_CENTER[HORI_CENTER_IDX].weight = 100 - PARAMS_FOR_PADDING[LEFT_IDX].weight - PARAMS_FOR_PADDING[RIGHT_IDX].weight;
        centerViewGroup.addView(VIEWS_FOR_PADDING[LEFT_IDX], PARAMS_FOR_PADDING[LEFT_IDX]);
        centerViewGroup.addView(MAIN_VIEW_GROUP, PARAMS_FOR_CENTER[HORI_CENTER_IDX]);
        centerViewGroup.addView(VIEWS_FOR_PADDING[RIGHT_IDX], PARAMS_FOR_PADDING[RIGHT_IDX]);
        return centerViewGroup;
    }
}
