
package com.myproject.tastyhub;

public class DeliveryETA {

    private static final int PREPARATION_MINUTES = 15;
    private static final int DEFAULT_TRAVEL_MINUTES = 20;

    // Prevent unnecessary object creation
    private DeliveryETA() {
    }

    // Calculate estimated delivery time based on delivery area
    public static int estimateMinutes(String deliveryArea) {

        if (deliveryArea == null || deliveryArea.trim().isEmpty()) {
            return PREPARATION_MINUTES + DEFAULT_TRAVEL_MINUTES;
        }

        String area = deliveryArea.trim().toLowerCase();

        int travelMinutes;

        switch (area) {

            case "kampala main town":
                travelMinutes = 15;
                break;

            case "kasubi":
            case "rubaga":
                travelMinutes = 20;
                break;

            case "ntinda":
            case "bugolobi":
            case "bukoto":
            case "kisaasi":
                travelMinutes = 25;
                break;

            case "muyenga":
            case "ggaba":
            case "bunga":
                travelMinutes = 30;
                break;

            default:
                travelMinutes = DEFAULT_TRAVEL_MINUTES;
                break;
        }

        return PREPARATION_MINUTES + travelMinutes;
    }

    // Test the ETA calculation
    public static void main(String[] args) {

        String[] deliveryAreas = {
            "Kampala Main Town",
            "Kasubi",
            "Ntinda",
            "Muyenga",
            "Kansanga"
        };

        System.out.println("==================================");
        System.out.println(" TASTYHUB DELIVERY ETA TEST");
        System.out.println("==================================");

        for (String area : deliveryAreas) {

            int minutes = estimateMinutes(area);

            System.out.println(
                "Area: " + area
                + " | Estimated time: "
                + minutes + " minutes"
            );
        }

        System.out.println("==================================");
    }
}
