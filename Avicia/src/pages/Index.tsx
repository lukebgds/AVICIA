import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { 
  Stethoscope, 
  Shield, 
  Users, 
  FileText, 
  Clock, 
  ChevronRight,
  CheckCircle,
  ArrowRight
} from "lucide-react";
import { useNavigate } from "react-router-dom";

const Index = () => {
  const navigate = useNavigate();

  const features = [
    {
      icon: FileText,
      title: "Prontuário Digital",
      description: "Gerencie prontuários de forma segura e eficiente"
    },
    {
      icon: Users,
      title: "Múltiplos Usuários",
      description: "Admin, médicos, gestão e pacientes com níveis de acesso"
    },
    {
      icon: Shield,
      title: "Segurança Total",
      description: "Dados protegidos com criptografia avançada"
    },
    {
      icon: Clock,
      title: "Acesso 24/7",
      description: "Disponível a qualquer hora, em qualquer lugar"
    }
  ];

  const benefits = [
    "Redução de tempo de consulta",
    "Histórico médico completo",
    "Relatórios automatizados",
    "Integração com laboratórios",
    "Agenda inteligente",
    "Backup automático"
  ];

  return (
    <div className="min-h-screen bg-gradient-to-br from-medical-light via-background to-medical-secondary">
      {/* Hero Section */}
      <section className="container mx-auto px-6 py-20">
        <div className="text-center max-w-4xl mx-auto">
          <div className="flex items-center justify-center mb-6">
            <div className="bg-primary rounded-full p-4">
              <Stethoscope className="h-12 w-12 text-primary-foreground" />
            </div>
          </div>
          
          <h1 className="text-6xl font-bold mb-6 bg-gradient-to-r from-primary to-info bg-clip-text text-transparent">
            AVICIA
          </h1>
          
          <h2 className="text-3xl font-semibold mb-6 text-foreground">
            Prontuário Médico Eletrônico
          </h2>
          
          <p className="text-xl text-muted-foreground mb-8 leading-relaxed">
            Revolucione sua prática médica com nosso sistema completo de gestão de prontuários eletrônicos. 
            Seguro, intuitivo e desenvolvido especialmente para profissionais da saúde.
          </p>
          
          <div className="flex flex-col sm:flex-row gap-4 justify-center">
            <Button 
              size="lg" 
              className="bg-gradient-to-r from-primary to-info hover:from-primary/90 hover:to-info/90 transition-all duration-300 text-lg px-8 py-4"
              onClick={() => navigate("/login")}
            >
              Começar Agora
              <ArrowRight className="ml-2 h-5 w-5" />
            </Button>
            <Button 
              size="lg" 
              variant="outline" 
              className="border-primary text-primary hover:bg-primary hover:text-primary-foreground transition-all duration-300 text-lg px-8 py-4"
            >
              Saiba Mais
            </Button>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="container mx-auto px-6 py-16">
        <div className="text-center mb-12">
          <h3 className="text-3xl font-bold text-primary mb-4">
            Funcionalidades Principais
          </h3>
          <p className="text-muted-foreground text-lg">
            Tudo que você precisa para uma gestão médica eficiente
          </p>
        </div>
        
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          {features.map((feature, index) => (
            <Card key={index} className="shadow-[var(--medical-glow)] border-medical-secondary/30 hover:shadow-lg transition-all duration-300">
              <CardHeader className="text-center">
                <div className="bg-primary/10 rounded-full p-4 w-16 h-16 mx-auto mb-4 flex items-center justify-center">
                  <feature.icon className="h-8 w-8 text-primary" />
                </div>
                <CardTitle className="text-primary">{feature.title}</CardTitle>
              </CardHeader>
              <CardContent>
                <CardDescription className="text-center">
                  {feature.description}
                </CardDescription>
              </CardContent>
            </Card>
          ))}
        </div>
      </section>

      {/* Benefits Section */}
      <section className="container mx-auto px-6 py-16">
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-12 items-center">
          <div>
            <h3 className="text-3xl font-bold text-primary mb-6">
              Por que escolher o AVICIA?
            </h3>
            <p className="text-muted-foreground text-lg mb-6">
              Nossa plataforma foi desenvolvida com foco na experiência do usuário e na segurança dos dados médicos.
            </p>
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              {benefits.map((benefit, index) => (
                <div key={index} className="flex items-center space-x-3">
                  <CheckCircle className="h-5 w-5 text-success flex-shrink-0" />
                  <span className="text-foreground">{benefit}</span>
                </div>
              ))}
            </div>
          </div>
          
          <Card className="shadow-[var(--medical-glow)] border-medical-secondary/30">
            <CardHeader>
              <CardTitle className="text-primary">Tipos de Usuário</CardTitle>
              <CardDescription>
                Sistema com níveis de acesso personalizados
              </CardDescription>
            </CardHeader>
            <CardContent className="space-y-4">
              {[
                { role: "Administrador", desc: "Controle total do sistema", color: "bg-destructive" },
                { role: "Médico", desc: "Acesso a prontuários e consultas", color: "bg-primary" },
                { role: "Gestão/Secretário", desc: "Agendamento e relatórios", color: "bg-info" },
                { role: "Paciente", desc: "Visualização do próprio histórico", color: "bg-success" }
              ].map((user, index) => (
                <div key={index} className="flex items-center justify-between p-3 rounded-lg bg-medical-light">
                  <div className="flex items-center space-x-3">
                    <Badge className={`${user.color} text-white`}>
                      {user.role}
                    </Badge>
                    <span className="text-foreground">{user.desc}</span>
                  </div>
                  <ChevronRight className="h-4 w-4 text-muted-foreground" />
                </div>
              ))}
            </CardContent>
          </Card>
        </div>
      </section>

      {/* CTA Section */}
      <section className="container mx-auto px-6 py-16">
        <Card className="shadow-[var(--medical-glow)] border-medical-secondary/30 bg-gradient-to-r from-primary/5 to-info/5">
          <CardContent className="text-center py-12">
            <h3 className="text-3xl font-bold text-primary mb-4">
              Pronto para começar?
            </h3>
            <p className="text-muted-foreground text-lg mb-8 max-w-2xl mx-auto">
            </p>
            <Button 
              size="lg" 
              className="bg-gradient-to-r from-primary to-info hover:from-primary/90 hover:to-info/90 transition-all duration-300 text-lg px-8 py-4"
              onClick={() => navigate("/login")}
            >
              Criar Conta Grátis
              <ArrowRight className="ml-2 h-5 w-5" />
            </Button>
          </CardContent>
        </Card>
      </section>
    </div>
  );
};

export default Index;
