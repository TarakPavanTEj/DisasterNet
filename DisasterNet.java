
import java.util.*;

public class DisasterNet {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Emergency> requests = new ArrayList<>();

    static class Emergency {
        int id, priority;
        String location, description;
        boolean supplies, team;

        Emergency(int id, String location, String description, int priority) {
            this.id = id;
            this.location = location;
            this.description = description;
            this.priority = priority;
            supplies = false;
            team = false;
        }

        void display() {
            System.out.println("-----------------------------");
            System.out.println("Incident ID : " + id);
            System.out.println("Location    : " + location);
            System.out.println("Description : " + description);
            System.out.println("Priority    : " + priority);
            System.out.println("Supplies    : " + (supplies ? "Distributed" : "Pending"));
            System.out.println("Rescue Team : " + (team ? "Assigned" : "Pending"));
        }
    }

    // M1: Emergency keyword searching
    static void searchReports() {
        System.out.print("Enter keyword: ");
        String key = sc.nextLine().toLowerCase();
        boolean found = false;

        for (Emergency e : requests) {
            if (e.location.toLowerCase().contains(key) ||
                e.description.toLowerCase().contains(key)) {
                e.display();
                found = true;
            }
        }

        if (!found) System.out.println("No matching incidents found.");
    }

    // M2: Suffix Array
    static void suffixArray() {
        System.out.print("Enter text: ");
        String text = sc.nextLine();
        String[] suffixes = new String[text.length()];

        for (int i = 0; i < text.length(); i++)
            suffixes[i] = text.substring(i);

        Arrays.sort(suffixes);

        System.out.println("Suffix Array:");
        for (String s : suffixes) System.out.println(s);
    }

    // M3: Edit Distance
    static void editDistance() {
        System.out.print("Enter first word: ");
        String a = sc.nextLine();
        System.out.print("Enter second word: ");
        String b = sc.nextLine();

        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) dp[i][0] = i;
        for (int j = 0; j <= b.length(); j++) dp[0][j] = j;

        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
                dp[i][j] = Math.min(
                    Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                    dp[i - 1][j - 1] + cost
                );
            }
        }

        System.out.println("Edit Distance: " + dp[a.length()][b.length()]);
    }

    // M4: Edmonds-Karp Maximum Flow
    static int maxFlow(int[][] capacity, int source, int sink) {
        int n = capacity.length;
        int[][] residual = new int[n][n];

        for (int i = 0; i < n; i++)
            residual[i] = capacity[i].clone();

        int flow = 0;
        int[] parent = new int[n];

        while (true) {
            Arrays.fill(parent, -1);
            Queue<Integer> q = new LinkedList<>();
            q.add(source);
            parent[source] = source;

            while (!q.isEmpty() && parent[sink] == -1) {
                int u = q.poll();

                for (int v = 0; v < n; v++) {
                    if (parent[v] == -1 && residual[u][v] > 0) {
                        parent[v] = u;
                        q.add(v);
                    }
                }
            }

            if (parent[sink] == -1) break;

            int amount = Integer.MAX_VALUE;

            for (int v = sink; v != source; v = parent[v])
                amount = Math.min(amount, residual[parent[v]][v]);

            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                residual[u][v] -= amount;
                residual[v][u] += amount;
            }

            flow += amount;
        }

        return flow;
    }

    static void resourceFlow() {
        int[][] capacity = {
            {0, 3, 2, 0},
            {0, 0, 0, 2},
            {0, 0, 0, 3},
            {0, 0, 0, 0}
        };

        System.out.println("Maximum Resource Flow: " +
                           maxFlow(capacity, 0, 3));
    }

    // M5: Vertex Cover approximation
    static void vertexCover() {
        int[][] edges = {{0, 1}, {1, 2}, {2, 3}};
        boolean[] used = new boolean[4];
        ArrayList<Integer> cover = new ArrayList<>();

        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];

            if (!used[u] && !used[v]) {
                cover.add(u);
                cover.add(v);
                used[u] = true;
                used[v] = true;
            }
        }

        System.out.println("Vertex Cover: " + cover);
    }

    // M6: Randomized QuickSort
    static void quickSort(int[] a, int low, int high) {
        if (low >= high) return;

        int p = low + new Random().nextInt(high - low + 1);
        int temp = a[p];
        a[p] = a[high];
        a[high] = temp;

        int pivot = a[high], i = low - 1;

        for (int j = low; j < high; j++) {
            if (a[j] <= pivot) {
                i++;
                temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }

        temp = a[i + 1];
        a[i + 1] = a[high];
        a[high] = temp;

        int index = i + 1;
        quickSort(a, low, index - 1);
        quickSort(a, index + 1, high);
    }

    static void algorithmMenu() {
        while (true) {
            System.out.println("\n===== DSA Algorithms =====");
            System.out.println("1. Suffix Array");
            System.out.println("2. Edit Distance");
            System.out.println("3. Maximum Resource Flow");
            System.out.println("4. Vertex Cover");
            System.out.println("5. Randomized QuickSort");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    suffixArray();
                    break;
                case 2:
                    editDistance();
                    break;
                case 3:
                    resourceFlow();
                    break;
                case 4:
                    vertexCover();
                    break;
                case 5:
                    int[] a = {8, 3, 6, 1, 5};
                    quickSort(a, 0, a.length - 1);
                    System.out.println("Sorted Data: " + Arrays.toString(a));
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // Add Emergency Request
    static void addRequest() {
        System.out.print("Enter incident ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Emergency e : requests) {
            if (e.id == id) {
                System.out.println("Incident ID already exists!");
                return;
            }
        }

        System.out.print("Enter location: ");
        String location = sc.nextLine();

        System.out.print("Enter description: ");
        String description = sc.nextLine();

        System.out.print("Enter priority (1-5): ");
        int priority = sc.nextInt();
        sc.nextLine();

        if (priority < 1 || priority > 5) {
            System.out.println("Invalid priority! Enter 1 to 5.");
            return;
        }

        requests.add(new Emergency(id, location, description, priority));
        System.out.println("Emergency request added!");
    }

    // View Emergency Requests
    static void viewRequests() {
        if (requests.isEmpty()) {
            System.out.println("No emergency requests found.");
            return;
        }

        System.out.println("\n===== Emergency Requests =====");
        for (Emergency e : requests) e.display();
    }

    // Distribute Supplies
    static void distributeSupplies() {
        System.out.print("Enter incident ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Emergency e : requests) {
            if (e.id == id) {
                e.supplies = true;
                System.out.println("Emergency supplies distributed to " + e.location);
                return;
            }
        }

        System.out.println("Incident ID not found.");
    }

    // Assign Rescue Team
    static void assignTeam() {
        System.out.print("Enter incident ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Emergency e : requests) {
            if (e.id == id) {
                e.team = true;
                System.out.println("Rescue team assigned to " + e.location);
                return;
            }
        }

        System.out.println("Incident ID not found.");
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n================================");
            System.out.println("       DisasterNet System");
            System.out.println(" Emergency Response Platform");
            System.out.println("================================");
            System.out.println("1. Add Emergency Request");
            System.out.println("2. View Emergency Requests");
            System.out.println("3. Search Incident Reports");
            System.out.println("4. Distribute Emergency Supplies");
            System.out.println("5. Assign Rescue Teams");
            System.out.println("6. DSA Algorithms");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addRequest();
                    break;
                case 2:
                    viewRequests();
                    break;
                case 3:
                    searchReports();
                    break;
                case 4:
                    distributeSupplies();
                    break;
                case 5:
                    assignTeam();
                    break;
                case 6:
                    algorithmMenu();
                    break;
                case 0:
                    System.out.println("DisasterNet closed. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
