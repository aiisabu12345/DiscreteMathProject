import java.util.Stack;

public class InfixTranfromer {
    public String findInToPost(String infix){
        Stack<Character> op = new Stack<>();
        Stack<String> post = new Stack<>();
        int i = 0;

        while(i< infix.length()){
            if(infix.charAt(i) >= '0' && infix.charAt(i) <= '9'){
                String tem = "";
                while(i< infix.length() && infix.charAt(i) >= '0' && infix.charAt(i) <= '9'){
                    tem += infix.charAt(i);
                    i++;
                }
                post.push(tem);
            }
            else{
                if(infix.charAt(i) == ')'){
                    while(!op.isEmpty()&&op.peek() != '('){
                        post.push(String.valueOf(op.pop()));
                    }
                    if(!op.isEmpty())op.pop();
                    i++;
                }
                else if(!op.isEmpty() && getScore(op.peek()) < 4 &&getScore(infix.charAt(i)) < 4 && getScore(infix.charAt(i)) <= getScore(op.peek())){
                    post.push(String.valueOf(op.pop()));
                }
                else{
                    op.push(infix.charAt(i));
                    i++;
                }
            }
        }


        while(!op.isEmpty()){
            char cur = op.pop();
            if(getScore(cur) < 4){
                post.push(String.valueOf(cur));
            }
        }

        String out = "";
        while(!post.isEmpty()){
            out = " " + post.pop() + out;
        }
        return out;
    }

    public String findInToPre(String infix){
        infix = reverse(infix);
       Stack<Character> op = new Stack<>();
        Stack<String> post = new Stack<>();
        int i = 0;

        while(i< infix.length()){
            if(infix.charAt(i) >= '0' && infix.charAt(i) <= '9'){
                String tem = "";
                while(i< infix.length() && infix.charAt(i) >= '0' && infix.charAt(i) <= '9'){
                    tem += infix.charAt(i);
                    i++;
                }
                post.push(tem);
            }
            else{
                if(infix.charAt(i) == ')'){
                    while(!op.isEmpty()&&op.peek() != '('){
                        post.push(String.valueOf(op.pop()));
                    }
                    if(!op.isEmpty())op.pop();
                    i++;
                }
                else if(!op.isEmpty() && getScore(op.peek()) < 4 &&getScore(infix.charAt(i)) < 4 && getScore(infix.charAt(i)) < getScore(op.peek())){
                    post.push(String.valueOf(op.pop()));
                }
                else{
                    op.push(infix.charAt(i));
                    i++;
                }
            }
        }


        while(!op.isEmpty()){
            char cur = op.pop();
            if(getScore(cur) < 4){
                post.push(String.valueOf(cur));
            }
        }

        String out = "";
        while(!post.isEmpty()){
            out = " " + post.pop()+ out;
        }
        return reverse(out).trim();
    }
    
    public int callLikeAlu(String s){
        System.out.println("Infix = "+s);
        String postfix = findInToPost(s);
        System.out.println("Postfix = "+postfix);

        String[] splitPost = postfix.split(" ");
        Stack<Integer> stack = new Stack<>();
        System.out.println("\n======================\nProcess:");
        for(String sp : splitPost){
            if(sp.length()>0&&sp.charAt(0) >= '0'&&sp.charAt(0)<='9'){
                System.out.println("Push: "+sp+" in stack");
                stack.push(Integer.parseInt(sp));
            }
            else if(stack.size() > 1){
                int second = stack.pop();
                int first = stack.pop();
                System.out.printf("Pop %d out of stack\n",second);
                System.out.printf("Pop %d out of stack\n",first);
                int out = 0;
                switch (sp) {
                    case "+" -> out = first + second;
                    case "-" -> out = first - second;
                    case "*" -> out = first * second;
                    case "/" -> out = first / second;
                    case "^" -> out = (int)Math.pow(first, second);
                }
                System.out.printf("Calculate: %d %s %d = %d\n",first,sp,second,out);
                System.out.printf("Push: %d in stack\n",out);
                stack.push(out);
            }
        }
        int ans = stack.pop();
        System.out.printf("Pop %d out of stack to be answer\n======================\n\n",ans);
        return ans;
    }

    public String reverse(String s){
        String newS = "";
        char cur;
        for(int i = s.length() - 1;i>=0;i--){
            cur = s.charAt(i);
            if(cur == ')')
                cur = '(';
            else if(cur == '(')
                cur = ')';
            newS = newS + cur;
        }
        return  newS;
    }

    public int getScore(char op){
        switch (op) {
            case '(':
            case ')':
                return 4;
            case '^':
                return 3;
            case '*':
            case '/':
                return 2;
            case '+':
            case '-':
                return 1;
        }
        return -1;
    }
}