package com.threepillar.meetingroomfinder.model;

import android.content.Context;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.FreeBusyCalendar;
import com.google.api.services.calendar.model.FreeBusyRequest;
import com.google.api.services.calendar.model.FreeBusyRequestItem;
import com.google.api.services.calendar.model.FreeBusyResponse;
import com.threepillar.meetingroomfinder.common.util.NetworkUtill;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by mohd.irfan on 6/14/2017.
 */

public class NetHandler {


    private EventDateTime getEventDateTime(String time) {
        DateTime startDateTime = new DateTime(time/*"2017-06-21T09:00:00-07:00"*/);
        EventDateTime eventDateTime = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("India/Delhi"/*"America/Los_Angeles"*/);
        return eventDateTime;
    }

    public synchronized ArrayList<Room> fetchAllRooms(Context context) {
        ArrayList<Room> roomList = new ArrayList<>();
        try {
            System.out.println("=========here 1==");
            CalendarList calendarList = NetworkUtill.getGoogleCalenderInstance(context).calendarList().list().execute();
            System.out.println("===========" + calendarList.getItems().toString());
            if (calendarList != null && calendarList.getItems().size() > 0) {
                HashSet<Room> roomDatas = new HashSet<>();
                for (int i = 0; i < calendarList.getItems().size(); i++) {
                    filterResourcesRoom(calendarList.getItems().get(i), roomDatas);
                }
                roomList.addAll(roomDatas);
            }
        } catch (IOException e) {
            System.out.println("=========here 2=="+e);
            e.printStackTrace();
        }

        return roomList;

    }


    private void filterResourcesRoom(CalendarListEntry entry, HashSet<Room> roomList) {
        String summary = entry.getSummary();
        if (NetworkUtill.isNotNull(entry.getId()) && NetworkUtill.isNotNull(summary)
                && summary.contains("Room")) {
            Room roomData = new Room();
            roomData.setRoomID(entry.getId());
            roomData.setRoomName(summary);
            roomList.add(roomData);
        }
    }

    public void fetchAvailableRooms(Context context, long startTime, long endTime) {
        if (startTime < System.currentTimeMillis())
            return;
        ArrayList<Room> list = NetworkUtill.dSerializeList(context);
        if (list == null || list.size() <= 0)
            return;
        try {
            FreeBusyResponse response = NetworkUtill.getGoogleCalenderInstance(context)
                    .freebusy().query(createFreeBusyRequest(list, startTime, endTime)).execute();

            Set keyset = response.getCalendars().entrySet();

            Iterator iterator = keyset.iterator();
            while (iterator.hasNext()) {
                FreeBusyCalendar freeBusyCalendar = (FreeBusyCalendar) iterator.next();
                if (freeBusyCalendar.getBusy().size() <= 0) {
                    iterator.remove();
                }
            }

//            System.out.println("==============" + keyset.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private FreeBusyRequest createFreeBusyRequest(ArrayList<Room> list, long startTime, long endTime) {
        DateTime now = new DateTime(startTime);
        FreeBusyRequest query = new FreeBusyRequest();
        query.setTimeMin(now);
        query.setTimeMax(new DateTime(endTime));
        query.setTimeZone("America/Los_Angeles"/*"india"*/);
        query.setCalendarExpansionMax(101);
        ArrayList<FreeBusyRequestItem> freeBusyRequestItemArrayList = new ArrayList<FreeBusyRequestItem>();
        for (int i = 0; i < list.size(); i++) {
            if (NetworkUtill.isNotNull(list.get(i).getRoomID()))
                freeBusyRequestItemArrayList.add(new FreeBusyRequestItem().set("id", list.get(i).getRoomID()));
        }
        query.setItems(freeBusyRequestItemArrayList);
        return query;
    }

    public void bookRoom(Context context, String roomName, String meetingHeadline,
                         String startTime, String endTime, ArrayList<String> mailIdList) {
        Event event = new Event()
                .setSummary(roomName)
                .setLocation("Noida,India")
                .setDescription(meetingHeadline);

        EventDateTime start = getEventDateTime(startTime);
        event.setStart(start);

        EventDateTime eventDateTime = getEventDateTime(endTime);
        event.setEnd(eventDateTime);

        //set Attendies list
        setAttendies(mailIdList, event);
        //set reminders
        setReminders(event);
        String calendarId = "primary";
        try {
            NetworkUtill.getGoogleCalenderInstance(context).events()
                    .insert(calendarId, event).setSendNotifications(true).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("======" + event.getHtmlLink());
    }


    private void setAttendies(ArrayList<String> mailIdList, Event event) {
        if (event == null || mailIdList == null || mailIdList.size() <= 0)
            return;

//        EventAttendee[] attendees = new EventAttendee[]{
//                new EventAttendee().setEmail("rahul.pandey@3pillarglobal.com")
////                ,new EventAttendee().setEmail("sbrin@example.com"),
//        };
        ArrayList<EventAttendee> list = new ArrayList<>();
        for (int i = 0; i < mailIdList.size(); i++) {
            if (NetworkUtill.isNotNull(mailIdList.get(i))) {
                EventAttendee eventAttendee = new EventAttendee();
                eventAttendee.setEmail(mailIdList.get(i));
                list.add(eventAttendee);
            }
//            event.setAttendees(Arrays.asList(attendees));
            event.setAttendees(list);
        }
    }

    private void setReminders(Event event) {
        if (event == null)
            return;
        EventReminder[] reminderOverrides = new EventReminder[]{
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);
    }


}
