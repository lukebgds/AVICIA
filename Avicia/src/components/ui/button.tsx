import * as React from "react";
import { Slot } from "@radix-ui/react-slot";
import { cva, type VariantProps } from "class-variance-authority";
import { cn } from "@/lib/utils";

const baseClasses =
  "flex justify-center items-center rounded-[30px] font-medium transition-colors disabled:opacity-50 disabled:cursor-not-allowed";

export const buttonVariants = cva(baseClasses, {
  variants: {
    variant: {
      default: "bg-[#0061FE] text-white hover:bg-blue-700",
      destructive:
        "bg-destructive text-destructive-foreground hover:bg-destructive/90",
      outline:
        "border border-input bg-background hover:bg-accent hover:text-accent-foreground",
      secondary: "bg-secondary text-secondary-foreground hover:bg-secondary/80",
      ghost: "hover:bg-accent hover:text-accent-foreground",
      link: "text-primary underline-offset-4 hover:underline",
    },

    // === TAMANHOS ADAPTADOS DO SEU CÓDIGO ===
    size: {
      default: "px-6 py-4",
      full: "w-full px-6 py-4",
      sm: "px-4 py-2",
      lg: "px-8 py-5",
    },
  },

  defaultVariants: {
    variant: "default",
    size: "default",
  },
});

// === INTERFACE FINAL ===
export interface ButtonProps
  extends React.ButtonHTMLAttributes<HTMLButtonElement>,
    VariantProps<typeof buttonVariants> {
  asChild?: boolean;
}

// === COMPONENTE BUTTON ===
export const Button = React.forwardRef<HTMLButtonElement, ButtonProps>(
  ({ className, variant, size, asChild = false, ...props }, ref) => {
    const Comp = asChild ? Slot : "button";

    return (
      <Comp
        ref={ref}
        className={cn(buttonVariants({ variant, size, className }))}
        {...props}
      />
    );
  }
);

Button.displayName = "Button";
