import React from 'react';

// Função 'cn' simples para mesclar classes do Tailwind
const cn = (...classes: (string | undefined)[]) => classes.filter(Boolean).join(' ');

interface FormFieldProps extends React.InputHTMLAttributes<HTMLInputElement> {
  label: string;
  icon?: string;
  error?: string;
  className?: string; // Propriedade para classes externas
}

// Usamos forwardRef se precisarmos passar uma ref para o input
export const FormField = React.forwardRef<HTMLInputElement, FormFieldProps>(
  ({ label, icon, error, className, ...props }, ref) => {
    return (
      // --- ESTA É A CORREÇÃO ---
      // A classe 'className' (ex: "md:w-1/2") é aplicada aqui
      <div className={cn("w-full", className)}>
        <label className="text-[#1E255E] font-medium text-sm block mb-3">
          {label}
        </label>
        <div
          className={cn(
            'justify-center border flex w-full flex-col font-normal px-[26px] py-[18px] rounded-[30px] border-solid border-[rgba(30,37,94,0.50)] transition-colors focus-within:border-[#0061FE] focus-within:ring-2 focus-within:ring-[#0061FE] focus-within:ring-opacity-20',
            error && 'border-red-500 focus-within:border-red-500 focus-within:ring-red-500'
          )}
        >
          <div className="flex w-full max-w-full items-stretch gap-2">
            {icon && (
              <img
                src={icon}
                alt=""
                className="aspect-[1] object-contain w-6 shrink-0"
                aria-hidden="true"
              />
            )}
            <input
              ref={ref}
              className="text-[#1E255E] grow shrink w-full bg-transparent border-none outline-none placeholder:text-[#1E255E] placeholder:opacity-30"
              {...props}
            />
          </div>
        </div>
        {error && (
          <p className="text-red-500 text-sm mt-1" role="alert">
            {error}
          </p>
        )}
      </div>
    );
  }
);

FormField.displayName = 'FormField';