package com.example.djsu;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;
import android.text.style.LeadingMarginSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class CustomDayViewDecorator implements DayViewDecorator {
    private final int padding;

    public CustomDayViewDecorator(int padding) {
        this.padding = padding;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new LeadingMarginSpan.Standard(0, padding)); // 왼쪽 여백 추가 예시
    }
}