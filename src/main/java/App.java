import java.util.List;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class App {
    private ItemRepository itemRepository;
    private SalesPromotionRepository salesPromotionRepository;

    public App(ItemRepository itemRepository, SalesPromotionRepository salesPromotionRepository) {
        this.itemRepository = itemRepository;
        this.salesPromotionRepository = salesPromotionRepository;
    }

    public String bestCharge(List<String> inputs) {
        //TODO: write code here

        List<Item> items = itemRepository.findAll();
        List<SalesPromotion> salesPromotionList = salesPromotionRepository.findAll();
        List<String> relatedItems = salesPromotionList.get(1).getRelatedItems();

        StringBuffer sb = new StringBuffer();

        // 思路： 统计两种策略分别需要花多少钱 然后比较两种情况下哪种花的钱更少
        int sum1 = 0, sum2 = 0;
        boolean halfIsBetter = false; // 方案二更省钱的标志

        sb.append("============= Order details =============\n");

        for(String i : inputs){
            String[] s = i.split(" ");
            for(Item item : items){
                if(s[0].equals(item.getId())){
                    int itemTotal = Integer.parseInt(s[2]) * (int)item.getPrice();
                    sum1 += itemTotal;
                    if(relatedItems.contains(s[0])){
                        sum2 += itemTotal / 2;
                        halfIsBetter = true;
                    }else {
                        sum2 += itemTotal;
                    }
                    sb.append(item.getName() + " x " + s[2] + " = " + itemTotal + " yuan\n");
                }
            }
        }


        sb.append("-----------------------------------\n");
        if(sum1 >= 30 &&  sum2 >= sum1 - 6 ){ // 这种情况下采取方案1
            sum1 -= 6;
            sb.append("Promotion used:\n")
                    .append("满30减6 yuan，saving 6 yuan\n")
                    .append("-----------------------------------\n" + "Total：")
                    .append(sum1).append(" yuan\n")
                    .append("===================================");
        } else if(halfIsBetter){ // 这种情况下是采取方案二
            sb.append("Promotion used:\n")
                    .append("Half price for certain dishes (Braised chicken，Cold noodles)，saving " + (sum1 - sum2) + " yuan\n")
                    .append("-----------------------------------\n" + "Total：")
                    .append(sum2)
                    .append(" yuan\n")
                    .append("===================================");
        } else { // 默认
            sb.append("Total："+ sum1 +" yuan\n" +
                    "===================================");
        }

        String res = sb.toString();

        return res;
    }
}
